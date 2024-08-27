package br.com.api.docs.auth.service;

import br.com.api.docs.auth.domain.User;
import br.com.api.docs.auth.dto.TokenDTO;
import br.com.api.docs.auth.dto.UserDTO;
import br.com.api.docs.auth.exceptions.AlreadyRegisteredException;
import br.com.api.docs.auth.exceptions.InputException;
import br.com.api.docs.auth.repositories.UserRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${docs.secret}")
    private String secretKey;

    public void createUser(UserDTO userDTO) {
        this.userRepository.findByEmail(userDTO.getEmail())
                .ifPresentOrElse((action) -> {
                    throw new AlreadyRegisteredException("Já existe cadastro com esse email");
                }, () -> {
                    String passwordEncoded = this.passwordEncoder.encode(userDTO.getPassword());

                    User user = new User();
                    user.setEmail(userDTO.getEmail());
                    user.setPasword(passwordEncoded);

                    this.userRepository.save(user);
                });
    }

    public TokenDTO authenticateUser(UserDTO userDTO) {
        User user = this.userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new InputException("Email/Senha errados"));

        boolean matches = passwordEncoder.matches(userDTO.getPassword(), user.getPasword());

        if (!matches) {
            throw new InputException("Email/Senha errados");
        }
        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        Instant instant = Instant.now().plus(Duration.ofHours(3));
        String token = JWT.create()
                .withIssuer("docs-api")
                .withSubject(user.getId().toString())
                .withExpiresAt(instant)
                .sign(algorithm);

        return TokenDTO.builder()
                .access_token(token)
                .build();
    }
}

package br.com.api.docs.auth.service;

import br.com.api.docs.auth.domain.User;
import br.com.api.docs.auth.dto.ChangePasswordRequest;
import br.com.api.docs.auth.dto.TokenDTO;
import br.com.api.docs.auth.dto.UserRequest;
import br.com.api.docs.auth.dto.UserResponse;
import br.com.api.docs.auth.exceptions.AlreadyRegisteredException;
import br.com.api.docs.auth.exceptions.InputException;
import br.com.api.docs.auth.repositories.UserRepository;
import br.com.api.docs.auth.validator.ValidationResult;
import br.com.api.docs.auth.validator.strategy.UserPasswordValidator;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import br.com.api.docs.auth.validator.UserValidator;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserValidator userValidator;

    @Value("${docs.secret}")
    private String secretKey;

    public void createUser(UserRequest userDTO) {
        ValidationResult validationResult = userValidator.validate(userDTO);

        if (!validationResult.isValid()) {
            throw new InputException(validationResult.getMessage());
        }

        this.userRepository.findByEmailAndDocument(userDTO.getEmail(), userDTO.getDocument())
                .ifPresentOrElse((action) -> {
                    throw new AlreadyRegisteredException("Já existe cadastro com esse email");
                }, () -> {
                    String passwordEncoded = this.passwordEncoder.encode(userDTO.getPassword());

                    User user = new User();
                    user.setEmail(userDTO.getEmail());
                    user.setPasword(passwordEncoded);
                    user.setDocument(userDTO.getDocument());

                    this.userRepository.save(user);
                });
    }

    public TokenDTO authenticateUser(UserRequest userDTO) {
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

        UserResponse userResponse = UserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .document(user.getDocument())
                .build();

        return TokenDTO.builder()
                .access_token(token)
                .expiresIn(instant)
                .user(userResponse)
                .build();
    }

    public void alterPassword(UUID userId, ChangePasswordRequest changePasswordRequest) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new InputException("Email/Senha errados"));

        boolean matches = passwordEncoder.matches(changePasswordRequest.getOldPassword(), user.getPasword());

        if (matches) {
            ValidationResult validate = new UserPasswordValidator().validate(changePasswordRequest.getNewPassword());

            if (!validate.isValid()) {
                throw new InputException(validate.getMessage());
            }

            user.setPasword(changePasswordRequest.getNewPassword());
            this.userRepository.save(user);
        }
    }
}

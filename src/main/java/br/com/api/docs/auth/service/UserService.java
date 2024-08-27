package br.com.api.docs.auth.service;

import br.com.api.docs.auth.domain.User;
import br.com.api.docs.auth.dto.UserDTO;
import br.com.api.docs.auth.exceptions.AlreadyRegisteredException;
import br.com.api.docs.auth.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

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

    public void authenticateUser(UserDTO userDTO) {

    }
}

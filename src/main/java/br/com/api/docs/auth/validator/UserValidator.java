package br.com.api.docs.auth.validator;

import br.com.api.docs.auth.dto.UserDTO;
import br.com.api.docs.auth.validator.strategy.UserDocumentValidator;
import br.com.api.docs.auth.validator.strategy.UserEmailValidator;
import br.com.api.docs.auth.validator.strategy.UserPasswordValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserValidator {
    private final List<Validator<UserDTO>> validators;

    public UserValidator() {
        this.validators = List.of(
                new UserEmailValidator(),
                new UserPasswordValidator(),
                new UserDocumentValidator()
        );
    }

    public ValidationResult validate(UserDTO user) {
        for (Validator<UserDTO> validator : validators) {
            ValidationResult result = validator.validate(user);
            if (!result.isValid()) {
                return result;
            }
        }
        return ValidationResult.ok();
    }
}

package br.com.api.docs.auth.validator;

import br.com.api.docs.auth.dto.UserRequest;
import br.com.api.docs.auth.validator.strategy.UserDocumentValidator;
import br.com.api.docs.auth.validator.strategy.UserEmailValidator;
import br.com.api.docs.auth.validator.strategy.UserNameValidator;
import br.com.api.docs.auth.validator.strategy.UserPasswordValidator;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserValidator {
    private final List<Validator<UserRequest>> validators;

    public UserValidator() {
        this.validators = List.of(
                new UserEmailValidator(),
                new UserPasswordValidator(),
                new UserDocumentValidator(),
                new UserNameValidator()
        );
    }

    public ValidationResult validate(UserRequest user) {
        for (Validator<UserRequest> validator : validators) {
            ValidationResult result = validator.validate(user);
            if (!result.isValid()) {
                return result;
            }
        }
        return ValidationResult.ok();
    }
}

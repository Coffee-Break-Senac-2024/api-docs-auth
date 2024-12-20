package br.com.api.docs.auth.validator.strategy;

import br.com.api.docs.auth.dto.UserRequest;
import br.com.api.docs.auth.validator.ValidationResult;
import br.com.api.docs.auth.validator.Validator;

public class UserEmailValidator implements Validator<UserRequest> {

    @Override
    public ValidationResult validate(UserRequest userDTO) {
        if (userDTO.getEmail() == null) {
            return ValidationResult.error("Email não pode ser nulo.");
        }

        return ValidationResult.ok();
    }
}

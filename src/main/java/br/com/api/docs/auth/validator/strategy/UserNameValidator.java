package br.com.api.docs.auth.validator.strategy;

import br.com.api.docs.auth.dto.UserRequest;
import br.com.api.docs.auth.validator.ValidationResult;
import br.com.api.docs.auth.validator.Validator;

public class UserNameValidator implements Validator<UserRequest> {
    @Override
    public ValidationResult validate(UserRequest userDTO) {
        if (userDTO.getName().isBlank() || userDTO.getName().length() < 3) {
            return ValidationResult.error("Nome não pode ser vazio e deve conter mais de 3 caracteres.");
        }

        return ValidationResult.ok();
    }
}

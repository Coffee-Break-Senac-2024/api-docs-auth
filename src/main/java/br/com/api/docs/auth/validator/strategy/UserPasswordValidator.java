package br.com.api.docs.auth.validator.strategy;

import br.com.api.docs.auth.dto.UserDTO;
import br.com.api.docs.auth.validator.ValidationResult;
import br.com.api.docs.auth.validator.Validator;

public class UserPasswordValidator implements Validator<UserDTO> {
    @Override
    public ValidationResult validate(UserDTO user) {
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return ValidationResult.error("Password must be at least 6 characters long");
        }
        return ValidationResult.ok();
    }
}

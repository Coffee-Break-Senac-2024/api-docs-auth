package br.com.api.docs.auth.validator.strategy;

import br.com.api.docs.auth.dto.UserRequest;
import br.com.api.docs.auth.validator.ValidationResult;
import br.com.api.docs.auth.validator.Validator;

public class UserPasswordValidator implements Validator<UserRequest> {
    @Override
    public ValidationResult validate(UserRequest user) {
        if (user.getPassword() == null || user.getPassword().length() < 6) {
            return ValidationResult.error("Password must be at least 6 characters long");
        }
        return ValidationResult.ok();
    }

    public ValidationResult validate(String password) {
        if (password == null || password.length() < 6) {
            return ValidationResult.error("Password must be at least 6 characters long");
        }
        return ValidationResult.ok();
    }
}

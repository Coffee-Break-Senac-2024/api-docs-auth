package br.com.api.docs.auth.validator.strategy;

import br.com.api.docs.auth.dto.UserDTO;
import br.com.api.docs.auth.validator.ValidationResult;
import br.com.api.docs.auth.validator.Validator;

public class UserEmailValidator implements Validator<UserDTO> {

    @Override
    public ValidationResult validate(UserDTO userDTO) {
        if (userDTO.getEmail() == null) {
            return ValidationResult.error("Email não pode ser nulo.");
        }

        return ValidationResult.ok();
    }
}

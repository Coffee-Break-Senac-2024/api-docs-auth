package br.com.api.docs.auth.validator.strategy;

import br.com.api.docs.auth.dto.UserDTO;
import br.com.api.docs.auth.validator.ValidationResult;
import br.com.api.docs.auth.validator.Validator;

public class UserDocumentValidator implements Validator<UserDTO> {
    @Override
    public ValidationResult validate(UserDTO user) {
        if (user.getDocument() == null || user.getDocument().length() != 11) {
            return ValidationResult.error("Document must have exactly 11 digits");
        }
        return ValidationResult.ok();
    }
}

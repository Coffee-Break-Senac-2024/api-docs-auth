package br.com.api.docs.auth.validator.strategy;

import br.com.api.docs.auth.dto.UserRequest;
import br.com.api.docs.auth.validator.ValidationResult;
import br.com.api.docs.auth.validator.Validator;

public class UserDocumentValidator implements Validator<UserRequest> {
    @Override
    public ValidationResult validate(UserRequest user) {
        if (user.getDocument() == null || user.getDocument().length() != 11) {
            return ValidationResult.error("Document must have exactly 11 digits");
        }
        return ValidationResult.ok();
    }
}

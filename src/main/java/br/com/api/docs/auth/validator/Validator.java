package br.com.api.docs.auth.validator;

@FunctionalInterface
public interface Validator<T> {
    ValidationResult validate(T t);
}

package br.com.api.docs.auth.handler;

import br.com.api.docs.auth.exceptions.AlreadyRegisteredException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlerEntity extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AlreadyRegisteredException.class)
    public ResponseEntity<ErrorMessage> handleAlreadyRegisteredException(AlreadyRegisteredException e) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .status(404)
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(errorMessage);
    }


}
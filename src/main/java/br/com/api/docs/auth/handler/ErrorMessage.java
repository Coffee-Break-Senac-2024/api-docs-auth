package br.com.api.docs.auth.handler;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorMessage {
    private int status;
    private String message;
}

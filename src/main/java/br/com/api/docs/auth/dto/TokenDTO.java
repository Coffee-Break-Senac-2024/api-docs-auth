package br.com.api.docs.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class TokenDTO {
    private String access_token;
    private Instant expiresIn;
    private UserResponse user;
}

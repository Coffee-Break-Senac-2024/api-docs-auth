package br.com.api.docs.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDTO {

    private String access_token;

}

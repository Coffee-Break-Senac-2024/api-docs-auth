package br.com.api.docs.auth.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String email;
    private String password;
    private String document;

}

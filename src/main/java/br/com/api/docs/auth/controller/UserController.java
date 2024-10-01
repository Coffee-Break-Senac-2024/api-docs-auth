package br.com.api.docs.auth.controller;

import br.com.api.docs.auth.dto.TokenDTO;
import br.com.api.docs.auth.dto.UserDTO;
import br.com.api.docs.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody UserDTO user) {
        this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> auth(@RequestBody UserDTO user) {
        TokenDTO tokenResponse = this.userService.authenticateUser(user);
        return ResponseEntity.ok(tokenResponse);
    }

}
package br.com.api.docs.auth.controller;

import br.com.api.docs.auth.dto.ChangePasswordRequest;
import br.com.api.docs.auth.dto.TokenDTO;
import br.com.api.docs.auth.dto.UserRequest;
import br.com.api.docs.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody UserRequest user) {
        this.userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("");
    }

    @PostMapping("/auth")
    public ResponseEntity<TokenDTO> auth(@RequestBody UserRequest user) {
        TokenDTO tokenResponse = this.userService.authenticateUser(user);
        return ResponseEntity.ok(tokenResponse);
    }

    @PatchMapping("/change-password/{userId}")
    public ResponseEntity<Object> changePassword(@PathVariable UUID userId, @RequestBody ChangePasswordRequest changePasswordRequest) {
        this.userService.alterPassword(userId, changePasswordRequest);
        return ResponseEntity.noContent().build();
    }
}

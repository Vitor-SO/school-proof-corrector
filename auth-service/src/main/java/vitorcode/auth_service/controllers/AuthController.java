package vitorcode.auth_service.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitorcode.auth_service.dtos.LoginAdminRequest;
import vitorcode.auth_service.dtos.LoginAdminResponse;
import vitorcode.auth_service.services.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<LoginAdminResponse> login(@RequestBody @Valid LoginAdminRequest dto){
        return ResponseEntity.ok().body(authService.login(dto));
    }
}

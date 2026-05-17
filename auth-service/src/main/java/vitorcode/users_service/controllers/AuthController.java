package vitorcode.users_service.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitorcode.users_service.dtos.LoginAdminRequest;
import vitorcode.users_service.dtos.LoginAdminResponse;
import vitorcode.users_service.services.AuthService;

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

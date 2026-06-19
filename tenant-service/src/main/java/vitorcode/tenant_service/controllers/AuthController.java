package vitorcode.tenant_service.controllers;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vitorcode.tenant_service.database.master.entities.MasterUsersEntity;
import vitorcode.tenant_service.dtos.GetTenantAdminUserByUsername;
import vitorcode.tenant_service.dtos.LoginTenantUserRequest;
import vitorcode.tenant_service.dtos.LoginUserResponse;
import vitorcode.tenant_service.services.AuthService;

@RestController
@RequestMapping("/tenant")
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/admin/login")
    public ResponseEntity<MasterUsersEntity> adminLogin(@RequestBody @Valid GetTenantAdminUserByUsername username){
        return ResponseEntity.ok().body(authService.adminLogin(username));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(@RequestBody @Valid LoginTenantUserRequest request){
        return ResponseEntity.ok().body(authService.login(request));
    }
}

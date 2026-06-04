package vitorcode.tenant_service.dtos;

import java.util.UUID;

public record LoginUserResponse(
        UUID id,
        String email,
        String password,
        String role
) {
    public LoginUserResponse(UUID id, String email, String password, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}

package vitorcode.users_service.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginAdminRequest(
        @NotBlank(message = "username é obrigatorio") String username,
        @NotBlank(message = "password é obrigatorio") String password
) {}

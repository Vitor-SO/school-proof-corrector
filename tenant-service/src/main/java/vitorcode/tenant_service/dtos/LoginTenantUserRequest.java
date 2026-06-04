package vitorcode.tenant_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginTenantUserRequest(
        @NotBlank String slug,
        @Email @NotBlank String userEmail
) {
}

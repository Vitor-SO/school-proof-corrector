package vitorcode.tenant_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateTenantRequest(
        @NotBlank String schoolName,
        @NotBlank String slug,
        @Email @NotBlank String schoolEmail,
        @NotBlank String name,
        @Email @NotBlank String userEmail,
        @NotBlank String userPassword
) {
}

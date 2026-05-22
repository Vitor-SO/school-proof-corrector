package vitorcode.auth_service.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginTenantServiceRequest(
        @NotBlank String slug,
        @Email @NotBlank  String userEmail
) {
}

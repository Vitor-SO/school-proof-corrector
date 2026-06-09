package vitorcode.auth_service.dtos;

import java.util.UUID;

public record UserGenerateTokenDTO(
        UUID id,
        String tenant,
        String email,
        String role
) {
}

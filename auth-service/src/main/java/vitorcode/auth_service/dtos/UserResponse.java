package vitorcode.auth_service.dtos;

import java.time.Instant;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String name,
        String email,
        String password,
        String role,
        Instant createdAt,
        Instant updatedAt
) {
}

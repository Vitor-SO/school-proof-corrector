package vitorcode.auth_service.dtos;

import java.time.Instant;
import java.util.UUID;

public record AdminUserResponse(
   UUID id,
   String name,
   String username,
   String password,
   Instant createdAt,
   Instant updatedAt
) {}

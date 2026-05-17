package vitorcode.users_service.dtos;

import java.util.UUID;

public record AdminUserResponse(
   UUID id,
   String name,
   String username,
   String password,
   String createdAt,
   String updatedAt
) {}

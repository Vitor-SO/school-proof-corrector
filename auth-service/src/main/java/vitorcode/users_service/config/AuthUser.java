package vitorcode.users_service.config;

import vitorcode.users_service.interfaces.AuthenticableUser;

import java.util.List;
import java.util.UUID;

public record AuthUser(
        UUID id,
        String username,
        String password,
        List<String> roles,
        boolean enabled
) implements AuthenticableUser {
}

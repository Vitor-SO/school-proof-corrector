package vitorcode.auth_service.config;

import vitorcode.auth_service.interfaces.AuthenticableUser;

import java.util.List;
import java.util.UUID;

public record AuthUser(
        UUID id,
        String username,
        String password,
        List<String> roles
//        boolean enabled
) implements AuthenticableUser {
}

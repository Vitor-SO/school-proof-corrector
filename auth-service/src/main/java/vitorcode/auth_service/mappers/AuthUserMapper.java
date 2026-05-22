package vitorcode.auth_service.mappers;

import org.springframework.stereotype.Component;
import vitorcode.auth_service.config.AuthUser;
import vitorcode.auth_service.dtos.AdminUserResponse;
import vitorcode.auth_service.dtos.UserResponse;

import java.util.List;

@Component
public class AuthUserMapper {
    public AuthUser toAuthUser(AdminUserResponse admin) {
        return new AuthUser(
                admin.id(),
                admin.username(),
                admin.password(),
                null
        );
    }

    public AuthUser toAuthUser(UserResponse user) {
        return new AuthUser(
                user.id(),
                user.email(),
                user.password(),
                List.of(user.role())
        );
    }
}

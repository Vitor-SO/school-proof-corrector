package vitorcode.auth_service.mappers;

import org.springframework.stereotype.Component;
import vitorcode.auth_service.config.AuthUser;
import vitorcode.auth_service.dtos.AdminUserResponse;

@Component
public class AuthUserMapper {
    public AuthUser toAuthUser(AdminUserResponse admin) {
        return new AuthUser(
                admin.id(),
                admin.username(),
                admin.password(),
                null,
                true
        );
    }

//    public AuthUser toAuthUser(User user) {
//        return new AuthUser(
//                user.getId(),
//                user.getEmail(),
//                user.getPassword(),
//                null,
//                true
//        );
//    }
}

package vitorcode.users_service.mappers;

import org.springframework.stereotype.Component;
import vitorcode.users_service.config.AuthUser;
import vitorcode.users_service.dtos.AdminUserResponse;

import java.util.List;

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

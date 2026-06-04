package vitorcode.tenant_service.mappers;

import org.springframework.stereotype.Component;
import vitorcode.tenant_service.database.tenant.entities.TenantUsersEntity;
import vitorcode.tenant_service.dtos.LoginUserResponse;

@Component
public class LoginMapper {

    public LoginUserResponse toDTO(TenantUsersEntity entity){
        return new LoginUserResponse(
                entity.getId(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getRole().getName()
        );
    }
}

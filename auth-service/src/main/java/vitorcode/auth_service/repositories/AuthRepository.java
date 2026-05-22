package vitorcode.auth_service.repositories;

import org.springframework.stereotype.Repository;
import vitorcode.auth_service.datasources.TenantDataSource;
import vitorcode.auth_service.dtos.LoginRequest;
import vitorcode.auth_service.dtos.LoginTenantServiceRequest;
import vitorcode.auth_service.dtos.UserResponse;

@Repository
public class AuthRepository {
    private final TenantDataSource tenantDataSource;

    public AuthRepository(TenantDataSource tenantDataSource) {
        this.tenantDataSource = tenantDataSource;
    }

    public UserResponse tenantLogin(LoginRequest dto){
        return tenantDataSource.tenantLogin(new LoginTenantServiceRequest(dto.slug(), dto.userEmail()));
    }
}

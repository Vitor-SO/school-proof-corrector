package vitorcode.users_service.repositories;

import org.springframework.stereotype.Repository;
import vitorcode.users_service.datasources.TenantDataSource;
import vitorcode.users_service.dtos.AdminUserResponse;
import vitorcode.users_service.dtos.LoginTenantServiceRequest;

@Repository
public class AdminUserRepository {
    private final TenantDataSource tenantDataSource;

    public AdminUserRepository(TenantDataSource tenantDataSource) {
        this.tenantDataSource = tenantDataSource;
    }

    public AdminUserResponse findByUsername(String username){
        return tenantDataSource.getAdminUserByUsername(new LoginTenantServiceRequest(username));
    }
}

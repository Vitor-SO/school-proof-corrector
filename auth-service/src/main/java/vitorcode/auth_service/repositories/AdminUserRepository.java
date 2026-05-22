package vitorcode.auth_service.repositories;

import org.springframework.stereotype.Repository;
import vitorcode.auth_service.datasources.TenantDataSource;
import vitorcode.auth_service.dtos.AdminUserResponse;
import vitorcode.auth_service.dtos.GetTenantAdminUserByUsernameRequest;

@Repository
public class AdminUserRepository {
    private final TenantDataSource tenantDataSource;

    public AdminUserRepository(TenantDataSource tenantDataSource) {
        this.tenantDataSource = tenantDataSource;
    }

    public AdminUserResponse findByUsername(String username){
        return tenantDataSource.getAdminUserByUsername(new GetTenantAdminUserByUsernameRequest(username));
    }
}

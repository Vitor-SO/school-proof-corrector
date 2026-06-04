package vitorcode.tenant_service.services;

import org.springframework.stereotype.Service;
import vitorcode.tenant_service.database.master.entities.MasterUsersEntity;
import vitorcode.tenant_service.database.master.entities.TenantsEntity;
import vitorcode.tenant_service.database.tenant.entities.TenantUsersEntity;
import vitorcode.tenant_service.dtos.GetTenantAdminUserByUsername;
import vitorcode.tenant_service.dtos.LoginTenantUserRequest;
import vitorcode.tenant_service.dtos.LoginUserResponse;
import vitorcode.tenant_service.exceptions.*;
import vitorcode.tenant_service.mappers.LoginMapper;
import vitorcode.tenant_service.repositories.master.MasterRepository;
import vitorcode.tenant_service.repositories.master.MasterTenantsRepository;
import vitorcode.tenant_service.repositories.tenant.TenantRepository;

@Service
public class AuthService {
    private final MasterRepository masterRepository;
    private final MasterTenantsRepository masterTenantsRepository;
    private final TenantRepository tenantRepository;
    private final LoginMapper loginMapper;

    public AuthService(MasterRepository masterRepository, MasterTenantsRepository masterTenantsRepository, TenantRepository tenantRepository, LoginMapper loginMapper) {
        this.masterRepository = masterRepository;
        this.masterTenantsRepository = masterTenantsRepository;
        this.tenantRepository = tenantRepository;
        this.loginMapper = loginMapper;
    }

    public MasterUsersEntity adminLogin(GetTenantAdminUserByUsername dto){
        if(dto.username().isEmpty()){
            throw new UsernameIsNullException("[M-tenant.service.adminLogin] Username é obrigatorio");
        }

        return masterRepository.findByUsername(dto.username())
                .orElseThrow(() ->
                        new AdminUserNotFoundException(
                                "[M-tenant.service.login] admin user not found"
                        )
                );
    }

    public LoginUserResponse login(LoginTenantUserRequest dto){
        if(dto.slug().isEmpty() || dto.userEmail().isEmpty()){
            throw new LoginParamsIsNullException("[M-tenant.service.login] nome da escola e userEmail é obrigatorio");
        }

        TenantsEntity tenantDatabaseConnection = masterTenantsRepository.findByDbName(dto.slug())
                .orElseThrow(() ->
                        new SlugNotFoundException("[M-tenant.service.login] escola correspondente não foi encontrada")
                        );


        LoginUserResponse user = tenantRepository.findByUserEmail(tenantDatabaseConnection, dto);

        if(user == null){
            throw new UserNotFoundException("[M-tenant.service.login] usuario nao encontrado");
        }

        return user;
    }
}

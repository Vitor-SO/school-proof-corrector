package vitorcode.tenant_service.services;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vitorcode.tenant_service.dtos.CreateTenantRequest;
import vitorcode.tenant_service.repositories.tenant.TenantRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;

@Service
public class TenantService {
    private final TenantRepository tenantRepository;
    private final MigrationService migrationService;

    public TenantService(TenantRepository tenantRepository, MigrationService migrationService) {
        this.tenantRepository = tenantRepository;
        this.migrationService = migrationService;
    }

    public void createTenant(CreateTenantRequest dto){
        tenantRepository.createTenant(dto);
    }

    public void createUserDb(CreateTenantRequest dto){
        tenantRepository.createUserDb(dto);
    }

    public void grantPermissionToUser(CreateTenantRequest dto){
        tenantRepository.grantPermissionToUser(dto);
    }

    public void runMigrations(CreateTenantRequest dto){
        migrationService.migrateTenant(dto.slug(),dto.slug(),tenantRepository.getGeneratedUserPassword());
    }

    public void insertUser(CreateTenantRequest dto) {
        tenantRepository.insertUserDb(dto);
    }

    public void insertTenantConnectionIntoMaster(CreateTenantRequest dto) {
        tenantRepository.insertTenantConnectionIntoMaster(dto);
    }

    public void insertSchool(CreateTenantRequest dto) {
        tenantRepository.insertSchool(dto);
    }

    public void deleteTenant(CreateTenantRequest dto) {
        tenantRepository.deleteTenant(dto);
    }
}

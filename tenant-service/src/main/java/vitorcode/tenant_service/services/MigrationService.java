package vitorcode.tenant_service.services;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import vitorcode.tenant_service.config.providers.TenantDatasourceProvider;
import vitorcode.tenant_service.database.master.entities.MasterUsersEntity;
import vitorcode.tenant_service.database.master.entities.TenantsEntity;
import vitorcode.tenant_service.exceptions.MigrationPasswordEncriptedIsNullException;
import vitorcode.tenant_service.exceptions.TenantMigrationRunFailException;
import vitorcode.tenant_service.exceptions.TenantPasswordDecryptException;
import vitorcode.tenant_service.repositories.master.MasterRepository;
import vitorcode.tenant_service.repositories.master.MasterTenantsRepository;
import vitorcode.tenant_service.utils.AesCryptoProvider;

import javax.sql.DataSource;
import java.util.List;

@Service
public class MigrationService {
    @Value("${master.database.users.name}")
    private String name;
    @Value("${master.database.users.username}")
    private String username;
    @Value("${master.database.users.password}")
    private String password;

    private static final Logger log = LoggerFactory.getLogger(MigrationService.class);
    private final DataSource masterDataSource;
    private final MasterRepository masterRepository;
    private final TenantDatasourceProvider tenantDatasourceProvider;
    private final MasterTenantsRepository masterTenantsRepository;
    private final AesCryptoProvider aesCryptoProvider;

    public MigrationService(@Qualifier("masterDataSource") DataSource masterDataSource, MasterRepository masterRepository, TenantDatasourceProvider tenantDatasourceProvider, MasterTenantsRepository masterTenantsRepository, AesCryptoProvider aesCryptoProvider) {
        this.masterDataSource = masterDataSource;
        this.masterRepository = masterRepository;
        this.tenantDatasourceProvider = tenantDatasourceProvider;
        this.masterTenantsRepository = masterTenantsRepository;
        this.aesCryptoProvider = aesCryptoProvider;
    }

    public void migrateMaster() {
        log.info("🚀 Iniciando migração Flyway do banco master...");

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(masterDataSource)
                    .locations("classpath:db/migration/core")
                    .schemas("public")
                    .baselineOnMigrate(true)
                    .load();

            flyway.migrate();
            log.info("✅ Migração concluída com sucesso para o banco master!");
        } catch (Exception e) {
            log.error("❌ Falha ao executar migração do banco master: {}", e.getMessage(), e);
        }
    }

    public void insertAdminUserIntoMaster() {
        if(masterRepository.findByUsername("admin").isPresent()){
            return;
        }
        MasterUsersEntity masterUser = new MasterUsersEntity();
        masterUser.setName(name);
        masterUser.setUsername(username);
        String passwordEncripted = new BCryptPasswordEncoder().encode(password);
        if(passwordEncripted == null){
            throw new MigrationPasswordEncriptedIsNullException("A senha do usuario inical nao foi encriptada");
        }
        masterUser.setPassword(passwordEncripted);
        masterRepository.save(masterUser);
        log.info("Master Usuario admin inserido com sucesso!");
    }

    public void migrateTenant(String slug,String user,String password){
        log.info("🚀 Iniciando migração Flyway do banco tenant...");

        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(tenantDatasourceProvider.getDataSource(slug,user,password))
                    .locations("classpath:db/migration/tenant")
                    .schemas("public")
                    .baselineOnMigrate(true)
                    .load();

            flyway.migrate();
            log.info("✅ Migração concluída com sucesso para o banco tenant!");
        } catch (Exception e) {
            throw new TenantMigrationRunFailException("❌ Falha ao executar migração do banco tenant: "+ e.getMessage());
        }
    }

    public void runAllTenantMigrations(){
        List<TenantsEntity> tenantsDatabaseConnection = masterTenantsRepository.findAll();
        if(tenantsDatabaseConnection.isEmpty()){
            log.info("[tenant.MigrationService.runAllTenantMigrations] nenhuma conexao de tenant encontrada no master!");
            return;
        }

        tenantsDatabaseConnection.forEach(tenantConnection -> {
            String passwordDecrypted = aesCryptoProvider.decrypt(tenantConnection.getDbPassword());
            if(passwordDecrypted == null){
                throw new TenantPasswordDecryptException("[tenant.MigrationService.runAllTenantMigrations] nao foi possivel descriptografar a senha do tenant: {}");
            }
            this.migrateTenant(tenantConnection.getDbName(),tenantConnection.getDbUser(),passwordDecrypted);
        });

        log.info("✅ Migração de todos os tenants concluída com sucesso!");
    }
}

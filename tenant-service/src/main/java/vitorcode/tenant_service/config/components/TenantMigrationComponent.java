package vitorcode.tenant_service.config.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import vitorcode.tenant_service.services.MigrationService;

@Component
public class TenantMigrationComponent {
    private static final Logger logger = LoggerFactory.getLogger(TenantMigrationComponent.class);

    private final MigrationService migrationService;

    public TenantMigrationComponent(MigrationService migrationService) {
        this.migrationService = migrationService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        logger.info("Aplicação pronta, executando migração de todos os tenants...");
        migrationService.runAllTenantMigrations();
    }
}

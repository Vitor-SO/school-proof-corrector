package vitorcode.tenant_service.exceptions;

public class TenantMigrationRunFailException extends RuntimeException {
    public TenantMigrationRunFailException(String message) {
        super(message);
    }
}

package vitorcode.shared.config;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import vitorcode.shared.interfaces.IAesCryptoProvider;
import vitorcode.shared.interfaces.ITenantDatasourceProvider;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class NoOpConnectionProvider
        implements MultiTenantConnectionProvider<String> {

    private final DataSource masterDatasource;

    private final JdbcTemplate masterJdbc;

    private final ITenantDatasourceProvider tenantDatasourceProvider;

    private final IAesCryptoProvider aesCryptoProvider;

    private final Map<String, TenantInfo> tenantCache =
            new ConcurrentHashMap<>();

    public NoOpConnectionProvider(
            @Qualifier("masterDataSource")
            DataSource masterDatasource,

            ITenantDatasourceProvider tenantDatasourceProvider,

            IAesCryptoProvider aesCryptoProvider
    ) {

        this.masterDatasource = masterDatasource;

        this.masterJdbc = new JdbcTemplate(masterDatasource);

        this.tenantDatasourceProvider = tenantDatasourceProvider;

        this.aesCryptoProvider = aesCryptoProvider;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return masterDatasource.getConnection();
    }

    @Override
    public Connection getConnection(String tenantIdentifier)
            throws SQLException {

        TenantInfo tenantInfo =
                getTenantInfo(tenantIdentifier);

        DataSource tenantDatasource =
                tenantDatasourceProvider.getDataSource(
                        tenantInfo.dbName(),
                        tenantInfo.dbUser(),
                        aesCryptoProvider.decrypt(
                                tenantInfo.dbPassword()
                        )
                );

        return tenantDatasource.getConnection();
    }

    private TenantInfo getTenantInfo(String tenantId) {

        return tenantCache.computeIfAbsent(
                tenantId,
                this::fetchTenantConfigFromMaster
        );
    }

    private TenantInfo fetchTenantConfigFromMaster(
            String tenantId
    ) {

        return masterJdbc.queryForObject(
                """
                SELECT
                    db_name,
                    db_user,
                    db_password
                FROM tenants
                WHERE db_name = ?
                """,
                (rs, row) -> new TenantInfo(
                        rs.getString("db_name"),
                        rs.getString("db_user"),
                        rs.getString("db_password")
                ),
                tenantId
        );
    }

    private record TenantInfo(
            String dbName,
            String dbUser,
            String dbPassword
    ) {}

    @Override
    public void releaseAnyConnection(Connection connection)
            throws SQLException {

        connection.close();
    }

    @Override
    public void releaseConnection(
            String tenantIdentifier,
            Connection connection
    ) throws SQLException {

        connection.close();
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return false;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}

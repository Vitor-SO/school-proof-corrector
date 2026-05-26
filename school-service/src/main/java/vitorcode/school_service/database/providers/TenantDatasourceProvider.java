package vitorcode.school_service.database.providers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;import vitorcode.shared.interfaces.ITenantDatasourceProvider;

import javax.sql.DataSource;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TenantDatasourceProvider implements ITenantDatasourceProvider{

    @Value("${tenant.datasource.url}")
    private String tenantDatasourceUrl;

    private final Map<String, DataSource> cache =
            new ConcurrentHashMap<>();

    private final Logger logger =
            LoggerFactory.getLogger(this.getClass());

    public DataSource getDataSource(
            String databaseName,
            String user,
            String password
    ) {

        return cache.computeIfAbsent(databaseName, db -> {

            logger.info("Criando datasource tenant: {}", db);

            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(
                    tenantDatasourceUrl + databaseName
            );

            config.setUsername(user);

            config.setPassword(password);

            config.setDriverClassName(
                    "org.postgresql.Driver"
            );

            config.setMaximumPoolSize(10);

            config.setMinimumIdle(1);

            config.setPoolName("TENANT-" + databaseName);

            return new HikariDataSource(config);
        });
    }
}

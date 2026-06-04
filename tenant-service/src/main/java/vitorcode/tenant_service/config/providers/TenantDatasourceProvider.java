package vitorcode.tenant_service.config.providers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class TenantDatasourceProvider {
    @Value("${tenant.datasource.url}")
    private String tenantDatasourceUrl;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    public HikariDataSource getHikariDataSource(String databaseName, String user, String password){
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(tenantDatasourceUrl+databaseName);
            config.setUsername(user);
            config.setPassword(password);
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(1);
            return new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }

    public DataSource getDataSource(String databaseName, String user, String password){
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(tenantDatasourceUrl+databaseName);
            config.setUsername(user);
            config.setPassword(password);
            config.setMaximumPoolSize(3);
            config.setMinimumIdle(1);
            config.setAutoCommit(true);
            return new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados", e);
        }
    }
}

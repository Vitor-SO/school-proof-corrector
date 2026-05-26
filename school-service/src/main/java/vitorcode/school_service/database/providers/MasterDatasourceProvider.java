package vitorcode.school_service.database.providers;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;

@Configuration
public class MasterDatasourceProvider {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean(name = "masterDataSource")
    public DataSource masterDataSource() {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);

        config.setDriverClassName("org.postgresql.Driver");

        config.setMaximumPoolSize(10);
        config.setMinimumIdle(1);

        config.setPoolName("MASTER-POOL");

        return new HikariDataSource(config);
    }
}

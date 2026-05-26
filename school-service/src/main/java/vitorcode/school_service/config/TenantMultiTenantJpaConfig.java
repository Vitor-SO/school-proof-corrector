package vitorcode.school_service.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.MultiTenancySettings;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import vitorcode.shared.config.JpaVendorAdapterConfig;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(basePackages = "vitorcode.school_service.repositories",
        entityManagerFactoryRef = "tenantEntityManagerFactory",
        transactionManagerRef = "tenantTransactionManager")
public class TenantMultiTenantJpaConfig {

    @Bean(name = "tenantEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean tenantEntityManagerFactory(
            JpaVendorAdapterConfig vendorAdapter,
            MultiTenantConnectionProvider multiTenantConnectionProvider,
            CurrentTenantIdentifierResolver tenantIdentifierResolver) {

        var emf = new LocalContainerEntityManagerFactoryBean();
        emf.setPackagesToScan("vitorcode.school_service.database.entities");
        emf.setJpaVendorAdapter(vendorAdapter.jpaVendorAdapter());
        emf.setPersistenceUnitName("tenantPU");

        Map<String, Object> props = new HashMap<>();
        props.put(MultiTenancySettings.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
        props.put(MultiTenancySettings.MULTI_TENANT_IDENTIFIER_RESOLVER, tenantIdentifierResolver);

        props.put("hibernate.hbm2ddl.auto", "none");
        emf.setJpaPropertyMap(props);

        return emf;
    }


    @Bean(name = "tenantTransactionManager")
    public PlatformTransactionManager tenantTransactionManager(
            @Qualifier("tenantEntityManagerFactory") EntityManagerFactory tenantEmf) {
        return new JpaTransactionManager(tenantEmf);
    }
}
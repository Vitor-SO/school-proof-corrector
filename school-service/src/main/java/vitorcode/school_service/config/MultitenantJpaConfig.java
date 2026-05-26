package vitorcode.school_service.config;

import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vitorcode.shared.config.NoOpConnectionProvider;
import vitorcode.shared.config.TenantIdentifierResolver;

@Configuration
public class MultitenantJpaConfig {

    @Bean
    public HibernatePropertiesCustomizer hibernateCustomizer(
            NoOpConnectionProvider provider,
            TenantIdentifierResolver resolver
    ) {

        return props -> {

            props.put(
                    AvailableSettings.MULTI_TENANT_CONNECTION_PROVIDER,
                    provider
            );

            props.put(
                    AvailableSettings.MULTI_TENANT_IDENTIFIER_RESOLVER,
                    resolver
            );
        };
    }
}

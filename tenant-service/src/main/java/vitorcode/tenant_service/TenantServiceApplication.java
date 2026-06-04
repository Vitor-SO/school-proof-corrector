package vitorcode.tenant_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import vitorcode.shared.config.SecurityAutoConfiguration;

@SpringBootApplication(
        excludeName = {
                "vitorcode.shared.config.GatewaySecurityConfiguration"
        }
)
@Import(SecurityAutoConfiguration.class)
public class TenantServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TenantServiceApplication.class, args);
    }

}

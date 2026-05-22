package vitorcode.auth_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;
import vitorcode.shared.config.SecurityAutoConfiguration;

@SpringBootApplication(
        excludeName = {
                "vitorcode.shared.config.GatewaySecurityConfiguration"
        }
)
@EnableFeignClients
@Import(SecurityAutoConfiguration.class)
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}

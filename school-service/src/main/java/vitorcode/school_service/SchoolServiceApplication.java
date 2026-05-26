package vitorcode.school_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import vitorcode.shared.config.SecurityAutoConfiguration;

@SpringBootApplication(
        excludeName = {
                "vitorcode.shared.config.GatewaySecurityConfiguration"
        },
        scanBasePackages = {
                "vitorcode.shared",
                "vitorcode.school_service"
        }
)
@Import(SecurityAutoConfiguration.class)
public class    SchoolServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SchoolServiceApplication.class, args);
    }
}

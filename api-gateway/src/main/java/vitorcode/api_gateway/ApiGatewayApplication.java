package vitorcode.api_gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import vitorcode.shared.config.GatewaySecurityConfiguration;

@SpringBootApplication(
        excludeName = {"vitorcode.shared.config.SecurityAutoConfiguration"}
)
@Import(GatewaySecurityConfiguration.class)
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

}
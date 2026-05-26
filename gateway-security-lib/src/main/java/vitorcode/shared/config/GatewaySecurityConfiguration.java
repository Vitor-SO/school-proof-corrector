package vitorcode.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

import java.security.interfaces.RSAPublicKey;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(ServerHttpSecurity.class)
public class GatewaySecurityConfiguration {
    @Value("classpath:public.key")
    private RSAPublicKey publicKey;

    @Bean
    SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchange ->
                        exchange.pathMatchers(
                                "/auth/admin/login",
                                "/tenant/admin/login",
                                "/auth/login",
                                "/tenant/login").permitAll()
                                .anyExchange().authenticated()).
                oauth2ResourceServer(oauth -> oauth.jwt(Customizer.withDefaults()));
        return http.build();
    }

    @Bean
    ReactiveJwtDecoder reactiveJwtDecoder() {
        return NimbusReactiveJwtDecoder.withPublicKey(publicKey).build();
    }
}

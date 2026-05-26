package vitorcode.shared.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPublicKey;

@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(HttpSecurity.class)
public class SecurityAutoConfiguration {

    @Value("classpath:public.key")
    private RSAPublicKey publicKey;

    @Bean
    public JwtTenantFilter jwtTenantFilter(
            JwtDecoder jwtDecoder
    ) {
        return new JwtTenantFilter(jwtDecoder);
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withPublicKey(publicKey)
                .build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtTenantFilter jwtTenantFilter
    ) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/admin/login").permitAll()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/tenant/admin/login").permitAll()
                        .requestMatchers("/tenant/login").permitAll()
                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer(conf ->
                        conf.jwt(Customizer.withDefaults())
                );

        http.addFilterBefore(
                jwtTenantFilter,
                BearerTokenAuthenticationFilter.class
        );

        return http.build();
    }
}
package vitorcode.auth_service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import vitorcode.auth_service.config.JwtEncoderConfig;
import vitorcode.auth_service.dtos.UserGenerateTokenDTO;

import java.time.Instant;
import java.util.stream.Collectors;

@Service
public class JwtService {
    private static final Logger log = LoggerFactory.getLogger(JwtService.class);
    private final JwtEncoder jwtEncoder;
    private final JwtEncoderConfig jwtEncoderConfig;

    public JwtService(JwtEncoder jwtEncoder, JwtEncoderConfig jwtEncoderConfig) {
        this.jwtEncoder = jwtEncoder;
        this.jwtEncoderConfig = jwtEncoderConfig;
    }

    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expireSeconds = 3660L;
        Instant expireAt = now.plusSeconds(expireSeconds);

        String scopes = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("auth-service")
                .issuedAt(now)
                .expiresAt(expireAt)
                .subject(authentication.getName())
                .claim("scope", scopes)
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public boolean verifyPassword(String userPassword,String dbPassword){
        return jwtEncoderConfig.passwordEncoder().matches(userPassword,dbPassword);
    }

    public String generateToken(UserGenerateTokenDTO dto) {

        Instant now = Instant.now();

        long expireSeconds = 3660L;

        Instant expireAt = now.plusSeconds(expireSeconds);

        var claims = JwtClaimsSet.builder()
                .issuer("auth-service")
                .issuedAt(now)
                .expiresAt(expireAt)
                .subject(dto.email())
                .claim("id", dto.id())
                .claim("tenant", dto.tenant())
                .claim("email", dto.email())
                .claim("role", dto.role())
                .build();

        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}

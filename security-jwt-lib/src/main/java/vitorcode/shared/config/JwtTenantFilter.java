package vitorcode.shared.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

public class JwtTenantFilter extends OncePerRequestFilter {
    private final JwtDecoder jwtDecoder;


    public JwtTenantFilter(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }

        String token = header.substring(7);

        try{
            Jwt jwt = jwtDecoder.decode(token);;
            String tenant = jwt.getClaimAsString("tenant");

            if(tenant != null && !tenant.isBlank()){
                TenantContext.setCurrentTenant(tenant);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        try {
            filterChain.doFilter(request, response); // segue a requisição
        } finally {
            TenantContext.clear(); // limpa sempre, mesmo se der erro
        }
    }
}

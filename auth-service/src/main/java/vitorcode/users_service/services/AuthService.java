package vitorcode.users_service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vitorcode.users_service.dtos.LoginAdminRequest;
import vitorcode.users_service.dtos.LoginAdminResponse;
import vitorcode.users_service.exceptions.AdminAuthenticationFailException;
import vitorcode.users_service.exceptions.UsernameOrPasswordIsNullException;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public LoginAdminResponse login(LoginAdminRequest dto){
        if(dto.username().isEmpty() || dto.password().isEmpty()){
            throw new UsernameOrPasswordIsNullException("[M-user.service.login] username e senha sao obrigatorios");
        }

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    dto.username(),
                                    dto.password()
                            )
                    );

            return new LoginAdminResponse(jwtService.generateToken(authentication));

        } catch (BadCredentialsException ex){
            throw new AdminAuthenticationFailException(
                    "[M-auth.services.login] Verifique os dados de login"
            );
        }
    }

}

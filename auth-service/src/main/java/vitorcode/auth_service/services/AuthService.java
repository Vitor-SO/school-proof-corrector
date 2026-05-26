package vitorcode.auth_service.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vitorcode.auth_service.dtos.*;
import vitorcode.auth_service.exceptions.*;
import vitorcode.auth_service.repositories.AuthRepository;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthRepository authRepository;

    public AuthService(AuthenticationManager authenticationManager, JwtService jwtService, AuthRepository authRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.authRepository = authRepository;
    }

    public LoginAdminResponse adminLogin(LoginAdminRequest dto){
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

    public LoginResponse login(LoginRequest dto){
        if(dto.slug().isEmpty() || dto.userEmail().isEmpty() || dto.password().isEmpty()){
            throw new LoginParamsIsNullException("[M-auth.service.login] nome da escola, email e senha sao obrigatorios");
        }

        UserResponse user = authRepository.tenantLogin(dto);

        if(user == null){
            throw new UserNotFoundException("[M-auth.service.login] Login falhou nao foi possivel encontrar o usuario");
        }

        boolean isPasswordMatches = jwtService.verifyPassword(dto.password(),user.password());

        if(!isPasswordMatches){
            throw new LoginCredentialsFailed("[M-auth.service.login] Não foi possivel fazer o login, verifique os dados");
        }

        return new LoginResponse(jwtService.generateToken(
                new UserGenerateTokenDTO(dto.slug(), user.email(), user.role()))
        );

    }

}

package vitorcode.auth_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vitorcode.auth_service.dtos.AdminUserResponse;
import vitorcode.auth_service.exceptions.UserNotFoundException;
import vitorcode.auth_service.mappers.AuthUserMapper;
import vitorcode.auth_service.repositories.AdminUserRepository;

@Service
public class AdminUserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(AdminUserDetailsServiceImpl.class);
    private final AdminUserRepository adminUserRepository;
    private final AuthUserMapper mapper;

    public AdminUserDetailsServiceImpl(AdminUserRepository adminUserRepository, AuthUserMapper mapper) {
        this.adminUserRepository = adminUserRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AdminUserResponse admin = adminUserRepository.findByUsername(username);

        if(admin != null){
            return new UserAuthenticated(mapper.toAuthUser(admin));
        }

        throw new UserNotFoundException("[M-auth.config.UserDetailsServiceImpl] usuario admin master nao foi encontrado");
    }
}

package vitorcode.auth_service.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import vitorcode.auth_service.dtos.AdminUserResponse;
import vitorcode.auth_service.exceptions.UserNotFoundException;
import vitorcode.auth_service.mappers.AuthUserMapper;
import vitorcode.auth_service.repositories.AdminUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AdminUserRepository adminUserRepository;
    private final AuthUserMapper mapper;

    public UserDetailsServiceImpl(AdminUserRepository adminUserRepository, AuthUserMapper mapper) {
        this.adminUserRepository = adminUserRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        AdminUserResponse admin = adminUserRepository.findByUsername(username);

        if(admin != null){
            return new UserAuthenticated(mapper.toAuthUser(admin));
        }

        throw new UserNotFoundException("[M-auth.config.UserDatailsServiceImpl] user not found");
    }
}

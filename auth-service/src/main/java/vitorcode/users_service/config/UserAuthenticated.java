package vitorcode.users_service.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vitorcode.users_service.interfaces.AuthenticableUser;

import java.util.Collection;
import java.util.Collections;

public class UserAuthenticated implements UserDetails {
    private static final Logger log = LoggerFactory.getLogger(UserAuthenticated.class);
    private final AuthenticableUser user;

    public UserAuthenticated(AuthenticableUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(user.roles() == null){
            return Collections.emptyList();
        }

        return user.roles()
                .stream()
                .filter(role -> role != null && !role.isBlank())
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return user.password();
    }

    @Override
    public String getUsername() {
        return user.username();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}

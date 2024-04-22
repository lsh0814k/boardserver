package com.fem.boardserver.login.service.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserToken implements UserDetails {
    private final LoginUserInfo loginUserInfo;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<String> roles = List.of("ROLE_" + loginUserInfo.getRole());
        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getPassword() {
        return loginUserInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return loginUserInfo.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

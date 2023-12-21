package com.todaytrend.apigatewayserver.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserDetailsImpl implements UserDetails {
    private String uuid;
    private String role;
    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(String uuid, String role, Collection<? extends GrantedAuthority> authorities) {
        this.uuid = uuid;
        this.role = role;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        // 비밀번호 정보가 없다면 null 대신 ""를 반환
        return "";
    }

    @Override
    public String getUsername() {
        return uuid;
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정이 만료되지 않았음을 의미하는 true를 반환
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠기지 않았음을 의미하는 true를 반환
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 인증 정보가 만료되지 않았음을 의미하는 true를 반환
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정이 활성화되었음을 의미하는 true를 반환
        return true;
    }
}

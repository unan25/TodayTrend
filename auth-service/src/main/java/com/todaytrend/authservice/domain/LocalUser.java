package com.todaytrend.authservice.domain;

import com.todaytrend.authservice.domain.enum_.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "localuser")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LocalUser implements UserDetails, UserInterface { // UserDetails를 상속받아 인증 객체로 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "local_user_id")
    private Long localUserId;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String uuid;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private boolean active;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    // 사용자의 id를 반환 (고유한 값)
    @Override
    public String getUsername() {
        return getEmail();
    }

    // 계정 만료 여부 반환
    // true -> 만료 되지 않음
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 반환
    // true 잠금 되지 않음
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드 만료 여부 반환
    // true 만료 되지 않음
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부 반환
    // true 사용 가능
    @Override
    public boolean isEnabled() {
        return true;
    }
    
    // 회원 탈퇴 (active -> false)
    public void deactivate() {
        this.active = false;
    }

}

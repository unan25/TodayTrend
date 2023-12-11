package com.todaytrend.authservice.domain;

import com.todaytrend.authservice.domain.enum_.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "localuser")
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LocalUser implements UserInterface {

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

    // 비밀번호 변경 메서드
    public void updatePassword(String password){
        this.password = password;
    }
    
    // 회원 탈퇴 (active -> false)
    public void deactivate() {
        this.active = false;
    }

}

package com.todaytrend.userservice.domain;

import com.todaytrend.userservice.domain.enum_.Gender;
import com.todaytrend.userservice.domain.enum_.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @Table(name = "user")
@Getter @AllArgsConstructor @Builder
@NoArgsConstructor
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDateTime birth;

    private String name; // 별명

    private String nickname; // 아이디 @nickname

    private String website;

    private String introduce;

    private String profile_image;

    private LocalDateTime creatAt;

    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active;
}

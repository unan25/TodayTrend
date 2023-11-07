package com.todaytrend.userservice.domain;

import com.todaytrend.userservice.domain.enum_.Gender;
import com.todaytrend.userservice.domain.enum_.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity @Table(name = "user")
@Getter @AllArgsConstructor @Builder
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birth;

    private String name;

    private String nickname; // 아이디 @nickname

    private String website;

    private String introduce;

    private String profile_image;

    @CreatedDate
    @Column(updatable = false, name = "create_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(insertable = false, name = "update_at")
    private LocalDateTime updateAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active;

    private String uuid;
}

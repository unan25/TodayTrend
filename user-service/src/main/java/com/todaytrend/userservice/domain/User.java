package com.todaytrend.userservice.domain;

import com.todaytrend.userservice.domain.enum_.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity @Table(name = "users")
@Getter @AllArgsConstructor @Builder
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birth;

    private String name;

    @Column(unique = true)
    private String nickname; // 아이디 @nickname

    private String website;

    private String introduction;

    private String profileImage;

    @CreatedDate
    @Column(updatable = false, name = "create_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(insertable = false, name = "update_at")
    private LocalDateTime updateAt;

    private String uuid;

    // 프로필 수정
    public void updateUserInfo(String name, String nickname, String website, String introduction, String profileImage) {
        this.name = name;
        this.nickname = nickname;
        this.website = website;
        this.introduction = introduction;
        this.profileImage = profileImage;
    }
}

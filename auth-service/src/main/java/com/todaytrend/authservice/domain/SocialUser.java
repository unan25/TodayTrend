package com.todaytrend.authservice.domain;

import com.todaytrend.authservice.domain.enum_.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity(name = "socialUser")
@Getter
@RequiredArgsConstructor
public class SocialUser implements UserInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialUserId;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String uuid;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder(toBuilder = true)
    public SocialUser(String email, String uuid) {
        this.email = email;
        this.uuid = uuid;
    }

}

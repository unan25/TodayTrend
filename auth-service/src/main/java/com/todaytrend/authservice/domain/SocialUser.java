package com.todaytrend.authservice.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity(name = "socialUser")
@Getter
@RequiredArgsConstructor
public class SocialUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long socialUserId;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String uuid;

    @Column(columnDefinition = "boolean default true", nullable = false)
    private boolean active;

    @Builder(toBuilder = true)
    public SocialUser(String email, String uuid) {
        this.email = email;
        this.uuid = uuid;
    }

}

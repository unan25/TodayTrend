package com.todaytrend.authservice.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 redis 사용하게 되면 지울 redis에 옮길 예정
 */
@NoArgsConstructor
@Getter
@Entity
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long localUserId;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    public RefreshToken(Long localUserId, String refreshToken) {
        this.localUserId = localUserId;
        this.refreshToken = refreshToken;
    }

    public RefreshToken Update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}

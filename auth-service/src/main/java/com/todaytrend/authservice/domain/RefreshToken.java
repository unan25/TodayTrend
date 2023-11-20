package com.todaytrend.authservice.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
 redis 사용하게 되면 지울 redis에 옮길 예정
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "refresh_token", nullable = false)
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "uuid", referencedColumnName = "uuid")
    private LocalUser localUser;

    public RefreshToken(LocalUser localUser, String refreshToken) {
        this.localUser = localUser;
        this.refreshToken = refreshToken;
    }

    public RefreshToken Update(String newRefreshToken) {
        this.refreshToken = newRefreshToken;
        return this;
    }
}

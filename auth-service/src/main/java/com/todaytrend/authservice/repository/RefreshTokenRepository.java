package com.todaytrend.authservice.repository;

import com.todaytrend.authservice.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findById(Long id);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findByLocalUser_Uuid(String uuid);
    void deleteByRefreshToken(String refreshToken);
    void deleteByLocalUser_Uuid(String uuid);
}

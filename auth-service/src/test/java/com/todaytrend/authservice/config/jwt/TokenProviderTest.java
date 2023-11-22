package com.todaytrend.authservice.config.jwt;

import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.repository.LocalUserRepository;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TokenProviderTest {

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private LocalUserRepository localUserRepository;
    @Autowired
    private JwtProperties jwtProperties;

    @DisplayName("generateToken(): 유저 정보와 만료기간을 전달해 토큰을 만들 수 있다.")
    @Test
    void generateToken() {
        // given
        LocalUser testUser = localUserRepository.save(LocalUser.builder()
                .email("test888@test.com")
                .password("qwer1234!")
                .build());

        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then
        Long localUserId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("localUserId", Long.class);

        assertThat(localUserId).isEqualTo(testUser.getLocalUserId());
        System.out.println("token : " + token);
    }
    
    // validToken() 검증 테스트
    @DisplayName("validToken(): 만료된 토큰인 때에 유효성 검증에 실패")
    @Test
    void validToken_invalidToken() {
        // given
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime() - Duration.ofDays(7).toMillis()))
                .build().createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }
    
    @DisplayName("validToken() : 유효한 토큰인 때에 유효성 검증에 성공")
    @Test
    void validToken_validToken() {
        // given
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isTrue();
    }
    
    // getAuthentication() 검증테스트
    @DisplayName("getAuthentication() : 토큰 기반으로 인증 정보를 가져올 수 있다.")
    @Test
    void getAuthentication() {
        // given
        String userEmail = "test@test.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        // when
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then
        assertThat(((LocalUser) authentication.getPrincipal()).getEmail()).isEqualTo(userEmail);
    }
    
    // getLocalUserId() 검증 테스트
    @DisplayName("getLocalUserId() : 토큰으로 유저 ID를 가져올 수 있다.")
    @Test
    void getLocalUserId () {
        // given
        Long localUserId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("localUserId", localUserId))
                .build()
                .createToken(jwtProperties);

        // when
        Long localUserIdByToken = tokenProvider.getLocalUserId(token);

        // then
        assertThat(localUserIdByToken).isEqualTo(localUserId);
    }
}

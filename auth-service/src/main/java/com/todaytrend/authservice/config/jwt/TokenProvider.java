package com.todaytrend.authservice.config.jwt;

import com.todaytrend.authservice.domain.LocalUser;
import com.todaytrend.authservice.repository.LocalUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;
    private final LocalUserRepository localUserRepository;

    public String generateToken(LocalUser localUser, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), localUser);
    }
    
    // JWT 토큰 생성 메서드
    private String makeToken(Date expiry, LocalUser localUser){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ :JWT
                .setIssuer(jwtProperties.getIssuer()) // yml에 저장한 issuer 값
                .setIssuedAt(now) // iat : 현재 시간
                .setExpiration(expiry) // expiry 멤버 변숫값
                .setSubject(localUser.getEmail()) // 내용 sub : 유저의 이메일 추후에 uuid 대체?
                .claim("localUserId", localUser.getLocalUserId()) // 클레임 id : 유저 ID
                // 서명 : 비밀값과 함께 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    // JWT 토큰 유효성 검증 메서드
    public boolean validToken(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(jwtProperties.getSecretKey()) // 비밀값으로 복호화
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) { // 복호화 과정에서 에러가 나면 유효하지 않은 토큰
            return false;
        }
    }
    
    // 토큰 기반으로 인증 정보를 가져오는 메서드
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        LocalUser localUser = findLocalUserByEmail(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(
                localUser, token, localUser.getAuthorities());
        // 내가 짠 거
//        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("ROLE_USER"));
//        return new UsernamePasswordAuthenticationToken(
//                new org.springframework.security.core.userdetails.User(
//                        claims.getSubject()," ", authorities), token, authorities);
    }

    private LocalUser findLocalUserByEmail(String email) {
        return localUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 이메일 입니다. : " + email));
    }
    
    // 토큰 기반으로 유저 ID를 가져오는 메서드
    public Long getLocalUserId(String token) {
        Claims claims = getClaims(token);
        System.out.println("claims = " + claims.get("localUserId", Long.class));
        return claims.get("localUserId", Long.class);
    }

    private Claims getClaims(String token) {
        return Jwts.parser() // 클레임 조회
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }
}

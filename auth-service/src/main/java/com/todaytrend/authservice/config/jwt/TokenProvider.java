package com.todaytrend.authservice.config.jwt;

import com.todaytrend.authservice.domain.UserInterface;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class TokenProvider {

    private final JwtProperties jwtProperties;

    public TokenInfo generateToken(UserInterface userInterface, Duration expiredAt, String tokenName) {
        Date now = new Date();
        String token = makeToken(new Date(now.getTime() + expiredAt.toMillis()), userInterface);

        return new TokenInfo(token, (int) expiredAt.getSeconds());
    }

    // JWT 토큰 생성 메서드
    private String makeToken(Date expiry, UserInterface userInterface){
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) // 헤더 typ :JWT
                .setIssuer(jwtProperties.getIssuer()) // yml에 저장한 issuer 값
                .setIssuedAt(now) // iat : 현재 시간
                .setExpiration(expiry) // expiry 멤버 변숫값
                .setSubject(userInterface.getUuid()) // uuid로 생성
                .claim("uuid", userInterface.getUuid()) // 유저 uuid
                .claim("role", userInterface.getRole()) // 유저 Role
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
    
    // 토큰을 기반으로 UUID를 가져오는 메서드
    public String getUserUuid(String token){
        Claims claims = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

}

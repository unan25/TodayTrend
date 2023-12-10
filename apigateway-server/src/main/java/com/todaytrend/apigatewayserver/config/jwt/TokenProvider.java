package com.todaytrend.apigatewayserver.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
@RequiredArgsConstructor
public class TokenProvider {

    private final JwtProperties jwtProperties;

    // 토큰에서 클레임 추출
    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token).getBody();
    }
    
    // 토큰 클레임에서 Role 추출
    public String getRoleFromToken(String token) {
        try {
            Claims claims = extractAllClaims(token); // 토큰에서 모든 Claim 추출
            return claims.get("role", String.class); // Role 클레임 추출
        } catch (JwtException | IllegalArgumentException e) {
            return "유효한 토큰이 아닙니다. role 추출 실패";
        }
    }

    // todo : 레빗엠큐 연동 시, 메시지큐에 유저 정보를 담아 uuid를 이용해 유저 비교
    public String getUuidFromToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.get("uuid", String.class); // uuid 추출
        } catch (JwtException | IllegalArgumentException e ){
            return "유효한 토큰이 아닙니다. uuid 추출 실패";
        }
    }

    // 토큰의 유효성 검증 (토큰이 만료되지 않았는지, 토큰의 issuer가 올바른지 확인)
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token);
            return claims.getBody().getExpiration().after(new Date()) && claims.getBody().getIssuer().equals(jwtProperties.getIssuer());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}

package com.todaytrend.authservice.util;

import com.todaytrend.authservice.config.jwt.TokenInfo;
import com.todaytrend.authservice.config.jwt.TokenProvider;
import com.todaytrend.authservice.domain.UserInterface;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;

@AllArgsConstructor
@Component
public class CookieUtils {

    private final TokenProvider tokenProvider;

    // 쿠키 생성
    public Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        return cookie;
    }

    // 쿠키 설정
    public void setTokenCookies(UserInterface user, HttpServletResponse response) {
        TokenInfo accessTokenInfo = tokenProvider.generateToken(user, Duration.ofHours(1), "access_token");
//        TokenInfo accessTokenInfo = tokenProvider.generateToken(user, Duration.ofMinutes(1), "access_token");
        TokenInfo refreshTokenInfo = tokenProvider.generateToken(user, Duration.ofDays(7), "refresh_token");

        Cookie accessTokenCookie = createCookie("access_token", accessTokenInfo.getToken(), accessTokenInfo.getExpiresIn());
        Cookie refreshTokenCookie = createCookie("refresh_token", refreshTokenInfo.getToken(), refreshTokenInfo.getExpiresIn());

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
    }

    // 쿠키 삭제
    public void deleteCookies(HttpServletResponse response, String... cookieNames) {
        for(String name : cookieNames) {
            Cookie cookie = new Cookie(name, null);
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }
}

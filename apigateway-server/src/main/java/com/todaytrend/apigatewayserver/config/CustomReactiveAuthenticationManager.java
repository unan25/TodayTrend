package com.todaytrend.apigatewayserver.config;

import com.todaytrend.apigatewayserver.config.jwt.JwtAuthenticationToken;
import com.todaytrend.apigatewayserver.config.jwt.TokenProvider;
import com.todaytrend.apigatewayserver.config.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
@Component
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final TokenProvider tokenProvider;

    /* 인증 객체를 처리하여 인증된 사용자를 나태내는 새로운 인증 객체를 생성하는 역할 */

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        log.info("CustomReactiveAuthenticationManager.authenticate 호출");

        String token = authentication.getCredentials().toString();
        log.info("토큰 추출 token : " + token);

        String role = tokenProvider.getRoleFromToken(token);
        log.info("토큰에서 ROLE 추출 Role : " + role);

        String uuid = tokenProvider.getUuidFromToken(token);
        log.info("토큰에서 uuid 추출 UUID : " + uuid);

        if (role == null || !tokenProvider.validateToken(token)) {
            log.info("Token validation failed");
            return Mono.empty();
        }

        UserDetails userDetails = new UserDetailsImpl(uuid, role, Collections.singleton(new SimpleGrantedAuthority(role)));
        JwtAuthenticationToken jwtToken = new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities());

        return Mono.just(new UsernamePasswordAuthenticationToken(userDetails, jwtToken, userDetails.getAuthorities()));

    }

}

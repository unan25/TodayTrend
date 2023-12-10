package com.todaytrend.apigatewayserver.config;

import com.todaytrend.apigatewayserver.config.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import reactor.core.publisher.Mono;

import java.util.Collections;

@RequiredArgsConstructor
@Slf4j
public class CustomReactiveAuthenticationManager implements ReactiveAuthenticationManager {

    private final TokenProvider tokenProvider;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();
        log.info("토큰 추출 token : " + token);

        String role = tokenProvider.getRoleFromToken(token);
        log.info("토큰에서 ROLE 추출 Role : " + role);

        String uuid = tokenProvider.getUuidFromToken(token);
        log.info("토큰에서 uuid 추출 UUID : " + uuid);

        if (role == null || !tokenProvider.validateToken(token)) {
            return Mono.empty();
        }
        return Mono.just(new UsernamePasswordAuthenticationToken(role, null,
                Collections.singleton(new SimpleGrantedAuthority(role))));
    }
}

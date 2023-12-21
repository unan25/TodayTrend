package com.todaytrend.apigatewayserver.config;

import com.todaytrend.apigatewayserver.config.jwt.TokenProvider;
import com.todaytrend.apigatewayserver.config.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Slf4j
@Component
@RequiredArgsConstructor
public class CookieServerAuthenticationConverter implements ServerAuthenticationConverter {

    private final TokenProvider tokenProvider;

    /* HTTP 요청에서 토큰을 추출하고 이를 이용하여 인증 객체를 생성하는 역할 */

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(exchange)
                .flatMap(serverWebExchange ->
                        Mono.justOrEmpty(serverWebExchange.getRequest().getCookies().getFirst("access_token")))
                .flatMap(cookie -> Mono.justOrEmpty(cookie.getValue())) // 쿠키의 값(즉, JWT 토큰)을 가져옵니다.
                .flatMap(token -> {
                    log.info("Convert token : " + token);
                    Mono<Claims> claimsMono = tokenProvider.extractMonoAllClaims(token);
                    return claimsMono.flatMap(claims -> {
                        Mono<String> roleMono = tokenProvider.extractMonoRole(claims);
                        Mono<String> uuidMono = tokenProvider.extractMonoUuid(claims);
                        return Mono.zip(roleMono, uuidMono)
                                .flatMap(tuple -> {
                                    String role = tuple.getT1();
                                    log.info("Convert Role : " + role);
                                    String uuid = tuple.getT2();
                                    log.info("Convert uuid : " + uuid);
                                    UserDetails userDetails =
                                            new UserDetailsImpl(uuid, role,
                                                    Collections.singleton(new SimpleGrantedAuthority(role)));
                                    return Mono.just(new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities()));
                                });
                    });
                });
    }
}

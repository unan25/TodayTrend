package com.todaytrend.apigatewayserver.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
public class CookieServerAuthenticationConverter implements ServerAuthenticationConverter {

    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        log.info("쿠키에서 토큰 추출");
        return Mono.justOrEmpty(exchange.getRequest().getCookies().getFirst("access_token"))
                .map(HttpCookie::getValue)
                .map(token -> new UsernamePasswordAuthenticationToken(token, token));
    }
}

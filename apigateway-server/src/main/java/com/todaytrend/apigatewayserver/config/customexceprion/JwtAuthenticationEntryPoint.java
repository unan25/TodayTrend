package com.todaytrend.apigatewayserver.config.customexceprion;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.server.ServerAuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
public class JwtAuthenticationEntryPoint implements ServerAuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

    @Override
    public Mono<Void> commence(ServerWebExchange exchange, AuthenticationException exception) {
        if (exception.getCause() instanceof ExpiredJwtException) {
            return handleExpiredJwtException(exchange);
        } else {
            return handleOtherAuthenticationException(exchange);
        }
    }

    private Mono<Void> handleExpiredJwtException(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        exchange.getResponse().getHeaders().setContentType(APPLICATION_JSON);
        Map<String, Object> errorResponse = createErrorResponse("Token has expired");
        return writeErrorResponse(exchange.getResponse(), errorResponse);
    }

    private Mono<Void> handleOtherAuthenticationException(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return Mono.error(new AuthenticationException("Authentication failed") {});
    }

    private Mono<Void> writeErrorResponse(ServerHttpResponse response, Map<String, Object> errorResponse) {
        try {
            byte[] responseBytes = new ObjectMapper().writeValueAsBytes(errorResponse);
            return response.writeWith(Mono.just(response.bufferFactory().wrap(responseBytes)));
        } catch (IOException e) {
            log.error("Failed to write error response", e);
            return Mono.error(e);
        }
    }

    private Map<String, Object> createErrorResponse(String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", Instant.now().toString());
        errorResponse.put("message", message);
        return errorResponse;
    }
}
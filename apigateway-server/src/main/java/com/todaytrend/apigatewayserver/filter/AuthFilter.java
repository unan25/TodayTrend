//package com.todaytrend.apigatewayserver.filter;
//
//import com.todaytrend.apigatewayserver.config.jwt.TokenProvider;
//import com.todaytrend.apigatewayserver.filter.exceptionpath.ExceptionPathManager;
//import lombok.RequiredArgsConstructor;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import org.springframework.core.Ordered;
//import org.springframework.core.io.buffer.DataBuffer;
//import org.springframework.http.HttpCookie;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//import java.nio.charset.StandardCharsets;
//import java.util.Arrays;
//import java.util.List;
//
///*
//인증/인가 글로벌 필터
//*/
//@Component
//@RequiredArgsConstructor
//public class AuthFilter implements GlobalFilter, Ordered {
//    private final TokenProvider tokenProvider;
//    private final ExceptionPathManager exceptionPathManager;
//
//    /*
//        쿠키를 읽어서 토큰을 가져오고, 토큰의 유효성 검사 실시.
//        토큰이 유효하고, Role이 ADMIN, USER인 경우에 요청 처리.
//        그렇지 않은 경우에는 401 응답 및 에러 메시지 반환
//
//        게이트웨이 + 시큐리티 12/06
//     */
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String path = exchange.getRequest().getPath().toString();
//
//        // 예외 경로인 경우 필터 적용 x
//        if (exceptionPathManager.isExceptionPath(path)) {
//            return chain.filter(exchange);
//        }
//
//        // 요청에서 쿠키 추출
//        MultiValueMap<String, HttpCookie> cookies = exchange.getRequest().getCookies();
//
//        // 쿠키에서 access_token 추출
//        HttpCookie accessTokenCookie = cookies.getFirst("access_token");
//
//        // access_token이 없거나 토큰이 유효하지 않다면 401 Unauthorized 응답을 반환
//        if (accessTokenCookie == null || !tokenProvider.validateToken(accessTokenCookie.getValue())) {
//            return unauthorizedResponse(exchange);
//        }
//
//        // getRoleFromToken 메서드를 이용해 Role 추출 및 비교
//        String role = tokenProvider.getRoleFromToken(accessTokenCookie.getValue());
//        List<String> allowedRoles = Arrays.asList("ADMIN", "USER"); // asList 불변성 유지, 추가 및 삭제 용이
//        if (!allowedRoles.contains(role)) {
//            return unauthorizedResponse(exchange);
//        }
//
//        // 토큰이 유효하다면 정상적인 요청
//        return chain.filter(exchange);
//    }
//
//    // 인증 실패 Response 401 Unauthorized 응답 및 에러 메시지 반환 메서드
//    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
//        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//        DataBuffer buffer = exchange.getResponse().bufferFactory()
//                .wrap("Not a valid token !!!".getBytes(StandardCharsets.UTF_8));
//        return exchange.getResponse().writeWith(Mono.just(buffer));
//    }
//
//    @Override
//    public int getOrder() {
//        return -1; // 필터 우선순위 설정
//    }
//}

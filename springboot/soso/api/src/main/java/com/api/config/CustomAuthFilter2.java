package com.api.config;

import com.api.ApiApplication;
import com.common.restapi.RestAPI;
import com.common.url.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;


// Filter 예시 https://wildeveloperetrain.tistory.com/207
@Component
public class CustomAuthFilter2 extends AbstractGatewayFilterFactory<CustomAuthFilter2.Config> {
    @Autowired
    RestAPI restAPI;
    public CustomAuthFilter2() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
            System.out.println("111111111");
            ServerHttpRequest request = exchange.getRequest();

//             Request Header에 token이 존재하지 않을 때
            if(!request.getHeaders().containsKey("jwtToken")){
//                return handleUnAuthorized(exchange, "토큰이 존재하지 않음"); // 401 Error
                return chain.filter(exchange);
            }

            // Request Header 에서 token 문자열 받아오기
            List<String> token = request.getHeaders().get("jwtToken");
            String tokenString = Objects.requireNonNull(token).get(0);

            System.out.println("tokenString = " + tokenString);

            String a = restAPI.post(URL.SSO_SERVER + "/validateToken", tokenString, String.class);
            System.out.println("a = " + a);

            // 토큰 검증
//            if(!tokenString.equals("X.Y.Z")) {
//                return handleUnAuthorized(exchange, "토큰이 일치하지 않음"); // 토큰이 일치하지 않을 때
//            }
            System.out.println("222222222");
            return chain.filter(exchange); // 토큰이 일치할 때

        });
    }

    // 에러 핸들링
    private Mono<Void> handleUnAuthorized(ServerWebExchange exchange, String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add("Content-Type", "text/plain;charset=UTF-8");
        return response.writeWith(Mono.just(response.bufferFactory().wrap(message.getBytes(StandardCharsets.UTF_8))));
    }
    public static class Config {

    }
}
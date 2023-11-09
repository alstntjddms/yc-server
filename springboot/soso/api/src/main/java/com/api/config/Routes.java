package com.api.config;

import com.common.url.URL;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Routes {


    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder, CustomAuthFilter1 customAuthFilter1, CustomAuthFilter2 customAuthFilter2) {

        return builder
                .routes()
                // 카카오 서버 라우팅
                .route("kace_route", p -> p.path("/api/kace/**")
                        .filters(f -> f.filter(customAuthFilter1.apply(new CustomAuthFilter1.Config())))
                        .uri(URL.KACE_SERVER))

                // 로그인 서버 라우팅
                .route("sso_route", p -> p.path("/api/sso/**")
                        .filters(f -> f.filter(customAuthFilter2.apply(new CustomAuthFilter2.Config())))
                        .uri(URL.SSO_SERVER))

                // 로그 서버 라우팅
                .route("log_route", p -> p.path("/api/log/**")
//                        .filters(f -> f.filter(customAuthFilter.apply(new CustomAuthFilter.Config())))
                        .uri(URL.LOG_SERVER))
                .build();
    }

}

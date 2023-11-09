package com.common.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * https://medium.com/@odysseymoon/spring-webclient-%EC%82%AC%EC%9A%A9%EB%B2%95-5f92d295edc0 참고
 * 비동기 AsyncRestAPI
 * !! API Gateway서버는 비동기만 사용
 */
@Component
public class AsyncRestAPI {

    @Autowired
    private WebClient webClient;

    public Mono<String> get(String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorComplete();
    }

    public <T> Mono<T> post(String uri, Object object, Class<T> responseType) {
        return webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(object)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorComplete();
    }

    public <T> Mono<T> put(String uri, Object object, Class<T> responseType) {
        return webClient.put()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(object))
                .retrieve()
                .bodyToMono(responseType)
                .onErrorComplete();
    }

    public <T> Mono<T> delete(String uri, Object object, Class<T> responseType) {
        return webClient.method(HttpMethod.DELETE)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(object))
                .retrieve()
                .bodyToMono(responseType)
                .onErrorComplete();
    }

    public <T> Mono<T> patch(String uri, Object object, Class<T> responseType) {
        return webClient.patch()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(object))
                .retrieve()
                .bodyToMono(responseType)
                .onErrorComplete();
    }
}

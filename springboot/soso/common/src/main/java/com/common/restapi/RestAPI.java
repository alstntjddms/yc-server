package com.common.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;


/**
 * https://medium.com/@odysseymoon/spring-webclient-%EC%82%AC%EC%9A%A9%EB%B2%95-5f92d295edc0 참고
 * 동기 RestAPI
 !! API Gateway서버는 비동기만 사용
 */
@Component
public class RestAPI {

    @Autowired
    private WebClient webClient;

    public String get(String uri) {
        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

//    public String get(String uri, HttpHeaders headers) {
//        return webClient.get()
//                .uri(uri)
//                .headers(httpHeaders -> httpHeaders.addAll(headers))
//                .retrieve()
//                .bodyToMono(String.class)
//                .block();
//    }
    public <T> T post(String uri, Object object, Class<T> responseType) {
        return webClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(object)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <T> T put(String uri, Object object, Class<T> responseType) {
        return webClient.put()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(object)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <T> T delete(String uri, Object object, Class<T> responseType) {
        return webClient.method(HttpMethod.DELETE)
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(object)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

    public <T> T patch(String uri, Object object, Class<T> responseType) {
        return webClient.patch()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(object)
                .retrieve()
                .bodyToMono(responseType)
                .block();
    }

}

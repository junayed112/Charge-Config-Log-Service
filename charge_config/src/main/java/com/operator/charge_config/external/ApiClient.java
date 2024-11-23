package com.operator.charge_config.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ApiClient {

    @Autowired
    private WebClient webClient;

    public <T> Mono<T> get(String url, MultiValueMap<String, String> headers, Class<T> responseType) {
        return webClient.get()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to fetch data", e)));
    }

    public <T> Mono<T> post(String url, MultiValueMap<String, String> headers, Object requestBody, Class<T> responseType) {
        return webClient.post()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(requestBody) // Add the request body
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(e -> Mono.error(new RuntimeException("Failed to fetch data", e)));
    }
}

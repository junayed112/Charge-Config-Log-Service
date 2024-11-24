package com.operator.charge_config.external;

import com.operator.charge_config.base.ApiResponse;
import com.operator.charge_config.dto.response.ServiceChargeResponse;
import com.operator.charge_config.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
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
        if (responseType == ApiResponse.class) {
            return webClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(ServiceChargeResponse.class)
                    .map(serviceChargeResponse -> {
                        ApiResponse apiResponse = new ApiResponse();
                        apiResponse.setStatusCode(serviceChargeResponse.getStatusCode());
                        apiResponse.setMessage(serviceChargeResponse.getMessage());
                        return (T) apiResponse;
                    })
                    .onErrorResume(e -> {
                        System.err.println("Failure Response: " + e.getMessage());
                        if (e instanceof WebClientResponseException responseException) {
                            ApiResponse fallbackResponse = new ApiResponse();
                            fallbackResponse.setStatusCode(responseException.getRawStatusCode());
                            fallbackResponse.setMessage(responseException.getMessage());
                            return Mono.just((T) fallbackResponse);
                        }
                        return Mono.error(e);
                    });
        }
        return webClient.post()
                .uri(url)
                .headers(httpHeaders -> httpHeaders.addAll(headers))
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseType)
                .onErrorResume(e -> {
                    System.err.println("Failure Response: " + e.getMessage());
                    return Mono.error(e);
                });
    }
}

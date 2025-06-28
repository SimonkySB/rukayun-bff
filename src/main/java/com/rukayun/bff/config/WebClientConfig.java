package com.rukayun.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;


import com.rukayun.bff.exceptions.PassthroughException;

import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
           
            .filter((request, next) -> {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication instanceof JwtAuthenticationToken) {
                    String token = ((JwtAuthenticationToken) authentication).getToken().getTokenValue();
                    ClientRequest filtered = ClientRequest.from(request)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                        .build();
                    return next.exchange(filtered).flatMap(this::handleErrorIfPresent);
                }
                return next.exchange(request).flatMap(this::handleErrorIfPresent);
            });
    }


    private Mono<ClientResponse> handleErrorIfPresent(ClientResponse response) {
        if (response.statusCode().isError()) {
            return response.bodyToMono(String.class)
                .flatMap(body -> Mono.<ClientResponse>error(new PassthroughException(response.statusCode(), body)));
        }
        return Mono.just(response);
    }
}

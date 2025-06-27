package com.rukayun.bff.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin
public class UsuarioController {
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${rukayun.apis.usermanagerms}")
    private String baseUrl;

    private String url = "/usuarios";

    @GetMapping
	public Mono<ResponseEntity<String>> GetAll(@RequestParam Optional<String> organizacionId) {
       String fullUrl = baseUrl + url;
        if (organizacionId.isPresent()) {
            fullUrl += "?organizacionId=" + organizacionId.get();
        }

		return webClientBuilder.build()
            .get()
            .uri(URI.create(fullUrl))
            .retrieve()
            .toEntity(String.class);
	}

    
}

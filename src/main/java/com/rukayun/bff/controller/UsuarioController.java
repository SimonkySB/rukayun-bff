package com.rukayun.bff.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/usuarios")

public class UsuarioController {
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${rukayun.apis.usermanagerms}")
    private String baseUrl;

    private String url = "/usuarios";

    @GetMapping
	public Mono<ResponseEntity<String>> getAll(@RequestParam Optional<String> organizacionId) {
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

    @GetMapping("/{id}")
    public Mono<ResponseEntity<String>> findById(@PathVariable String id) {
        String fullUrl = baseUrl + url + "/" + id;
        return webClientBuilder.build()
                .get()
                .uri(URI.create(fullUrl))
                .retrieve()
                .toEntity(String.class);
    }

    @PostMapping
    public Mono<ResponseEntity<String>> create(@RequestBody String body) {
        String fullUrl = baseUrl + url;
        return webClientBuilder.build()
                .post()
                .uri(URI.create(fullUrl))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toEntity(String.class);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<String>> update(@PathVariable String id, @RequestBody String body) {
        String fullUrl = baseUrl + url + "/" + id;
        return webClientBuilder.build()
                .put()
                .uri(fullUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toEntity(String.class);
    }

    
}

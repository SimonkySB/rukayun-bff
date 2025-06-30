package com.rukayun.bff.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/organizaciones")

public class OrganizacionController {
    
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${rukayun.apis.petsmanagerms}")
    private String baseUrl;

    private String url = "/organizaciones";

    @GetMapping
	public Mono<ResponseEntity<String>> getAll() {
        String fullUrl = baseUrl + url;

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


    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<String>> delete(@PathVariable String id) {
        String fullUrl = baseUrl + url + "/" + id;
        return webClientBuilder.build()
                .delete()
                .uri(fullUrl)
                .retrieve()
                .toEntity(String.class);
    }


    @PostMapping("/{id}/usuarios/{usuarioId}")
    public Mono<ResponseEntity<String>> agregarUsuario(@PathVariable String id, @PathVariable String usuarioId) {
        String fullUrl = baseUrl + url + "/" + id + "/usuarios/" + usuarioId;

        return webClientBuilder.build()
                .post()
                .uri(URI.create(fullUrl))
                .retrieve()
                .toEntity(String.class);
    }

    @DeleteMapping("/{id}/usuarios/{usuarioId}")
    public Mono<ResponseEntity<String>> quitarUsuario(@PathVariable String id, @PathVariable String usuarioId) {
        String fullUrl = baseUrl + url + "/" + id + "/usuarios/" + usuarioId;

        return webClientBuilder.build()
                .delete()
                .uri(URI.create(fullUrl))
                .retrieve()
                .toEntity(String.class);
    }

    @GetMapping("/me")
    public Mono<ResponseEntity<String>> listOrganizacionesOwner() {
        String fullUrl = baseUrl + url + "/me";
        return webClientBuilder.build()
                .get()
                .uri(URI.create(fullUrl))
                .retrieve()
                .toEntity(String.class);
    }

    @PutMapping("/{id}/me")
    public Mono<ResponseEntity<String>> editarOrganizacionOwner(@PathVariable String id, @RequestBody String body) {
        String fullUrl = baseUrl + url + "/" + id + "/me";
        return webClientBuilder.build()
                .put()
                .uri(fullUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toEntity(String.class);
    }
}

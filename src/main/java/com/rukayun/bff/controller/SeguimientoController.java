package com.rukayun.bff.controller;

import java.net.URI;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/seguimientos")

public class SeguimientoController {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${rukayun.apis.adoptionmanagerms}")
    private String baseUrl;

    private String url = "/seguimientos";

    @GetMapping
	public Mono<ResponseEntity<String>> getAll(
        @RequestParam Optional<String> usuarioId,
        @RequestParam Optional<String> adopcionId
    ) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl + url);

        usuarioId.ifPresent(value -> uriBuilder.queryParam("usuarioId", value));
        adopcionId.ifPresent(value -> uriBuilder.queryParam("adopcionId", value));

        URI uri = uriBuilder.build().toUri();
		return webClientBuilder.build()
            .get()
            .uri(uri)
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
    public Mono<ResponseEntity<String>> crear(@RequestBody String body) {
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

    @PostMapping("/{id}/cerrar")
    public Mono<ResponseEntity<String>> cerrar(@PathVariable String id, @RequestBody String body) {
        String fullUrl = baseUrl + url + "/" + id + "/cerrar";
        return webClientBuilder.build()
                .post()
                .uri(fullUrl)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toEntity(String.class);
    }


    @GetMapping("/tipos")
    public Mono<ResponseEntity<String>> tipos() {
        String fullUrl = baseUrl + url + "/tipos";
        return webClientBuilder.build()
                .get()
                .uri(URI.create(fullUrl))
                .retrieve()
                .toEntity(String.class);
    }

    @GetMapping("/estados")
    public Mono<ResponseEntity<String>> estados() {
        String fullUrl = baseUrl + url + "/tipos";
        return webClientBuilder.build()
                .get()
                .uri(URI.create(fullUrl))
                .retrieve()
                .toEntity(String.class);
    }
}

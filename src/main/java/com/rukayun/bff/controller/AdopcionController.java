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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/adopciones")

public class AdopcionController {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${rukayun.apis.adoptionmanagerms}")
    private String baseUrl;

    private String url = "/adopciones";

    @GetMapping
	public Mono<ResponseEntity<String>> getAll(
        @RequestParam Optional<String> usuarioId,
        @RequestParam Optional<String> adopcionEstadoId,
        @RequestParam Optional<String> organizacionId
    ) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl + url);

        usuarioId.ifPresent(value -> uriBuilder.queryParam("usuarioId", value));
        adopcionEstadoId.ifPresent(value -> uriBuilder.queryParam("adopcionEstadoId", value));
        organizacionId.ifPresent(value -> uriBuilder.queryParam("organizacionId", value));

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


    @PostMapping("/solicitar")
    public Mono<ResponseEntity<String>> solicitar(@RequestBody String body) {
        String fullUrl = baseUrl + url + "/solicitar";
        return webClientBuilder.build()
                .post()
                .uri(URI.create(fullUrl))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(body)
                .retrieve()
                .toEntity(String.class);
    }



    @PostMapping("/{id}/rechazar")
    public Mono<ResponseEntity<String>> rechazar(@PathVariable String id) {
        String fullUrl = baseUrl + url + "/" + id + "/rechazar";
        return webClientBuilder.build()
                .post()
                .uri(URI.create(fullUrl))
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toEntity(String.class);
    }


    @PostMapping("/{id}/aprobar")
    public Mono<ResponseEntity<String>> aprobar(@PathVariable String id) {
        String fullUrl = baseUrl + url + "/" + id + "/aprobar";
        return webClientBuilder.build()
                .post()
                .uri(URI.create(fullUrl))
                .contentType(MediaType.APPLICATION_JSON)
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


    @GetMapping("/estados")
    public Mono<ResponseEntity<String>> getEstados() {
        String fullUrl = baseUrl + url + "/estados";
        return webClientBuilder.build()
                .get()
                .uri(URI.create(fullUrl))
                .retrieve()
                .toEntity(String.class);
    }

}

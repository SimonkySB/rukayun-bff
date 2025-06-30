package com.rukayun.bff.controller;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/animales")

public class AnimalController {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${rukayun.apis.petsmanagerms}")
    private String baseUrl;

    private String url = "/animales";


    @GetMapping
	public Mono<ResponseEntity<String>> getAll(
        @RequestParam Optional<String> search,
        @RequestParam Optional<String> minEdad,
        @RequestParam Optional<String> maxEdad,
        @RequestParam Optional<String> sexoId,
        @RequestParam Optional<String> nivelActividadId,
        @RequestParam Optional<String> tamanoId,
        @RequestParam Optional<String> especieId,
        @RequestParam Optional<String> organizacionId,
        @RequestParam Optional<String> comunaId,
        @RequestParam Optional<String> publicado,
        @RequestParam Optional<String> page,
        @RequestParam Optional<String> pageSize,
        @RequestParam Optional<String> sortBy,
        @RequestParam Optional<String> sortDescending


    ) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(baseUrl + url);

        search.ifPresent(value -> uriBuilder.queryParam("search", value));
        minEdad.ifPresent(value -> uriBuilder.queryParam("minEdad", value));
        maxEdad.ifPresent(value -> uriBuilder.queryParam("maxEdad", value));
        sexoId.ifPresent(value -> uriBuilder.queryParam("sexoId", value));
        nivelActividadId.ifPresent(value -> uriBuilder.queryParam("nivelActividadId", value));
        tamanoId.ifPresent(value -> uriBuilder.queryParam("tamanoId", value));
        especieId.ifPresent(value -> uriBuilder.queryParam("especieId", value));
        organizacionId.ifPresent(value -> uriBuilder.queryParam("organizacionId", value));
        comunaId.ifPresent(value -> uriBuilder.queryParam("comunaId", value));
        publicado.ifPresent(value -> uriBuilder.queryParam("publicado", value));
        page.ifPresent(value -> uriBuilder.queryParam("page", value));
        pageSize.ifPresent(value -> uriBuilder.queryParam("pageSize", value));
        sortBy.ifPresent(value -> uriBuilder.queryParam("sortBy", value));
        sortDescending.ifPresent(value -> uriBuilder.queryParam("sortDescending", value));

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


    @GetMapping("/sexos")
	public Mono<ResponseEntity<String>> getSexos() {
        String fullUrl = baseUrl + url + "/sexos";
		return webClientBuilder.build()
            .get()
            .uri(URI.create(fullUrl))
            .retrieve()
            .toEntity(String.class);
	}

    @GetMapping("/especies")
	public Mono<ResponseEntity<String>> getEspecies() {
        String fullUrl = baseUrl + url + "/especies";
		return webClientBuilder.build()
            .get()
            .uri(URI.create(fullUrl))
            .retrieve()
            .toEntity(String.class);
	}

    @GetMapping("/tamanos")
	public Mono<ResponseEntity<String>> getTamano() {
        String fullUrl = baseUrl + url + "/tamanos";
		return webClientBuilder.build()
            .get()
            .uri(URI.create(fullUrl))
            .retrieve()
            .toEntity(String.class);
	}

    @GetMapping("/nivelActividades")
	public Mono<ResponseEntity<String>> getNivelActividades() {
        String fullUrl = baseUrl + url + "/nivelActividades";
		return webClientBuilder.build()
            .get()
            .uri(URI.create(fullUrl))
            .retrieve()
            .toEntity(String.class);
	}


   @PostMapping(path = "/{id}/imagenes", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Mono<ResponseEntity<String>> agregarImagen(
            @PathVariable String id,
            @RequestParam("file") MultipartFile filePart) {

        String fullUrl = baseUrl + url + "/" + id + "/imagenes";

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", filePart.getResource())
            .filename(filePart.getOriginalFilename())
            .contentType(MediaType.parseMediaType(filePart.getContentType()));

        return webClientBuilder.build()
                .post()
                .uri(fullUrl)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .toEntity(String.class);
    }


    @DeleteMapping("/{id}/imagenes/{imagenId}")
    public Mono<ResponseEntity<String>> quitarImagen(@PathVariable String id, @PathVariable String imagenId) {
        String fullUrl = baseUrl + url + "/" + id + "/imagenes/" + imagenId;

        return webClientBuilder.build()
                .delete()
                .uri(fullUrl)
                .retrieve()
                .toEntity(String.class);
    }


    @GetMapping("/me")
	public Mono<ResponseEntity<String>> listAnimalesUsuario() {
        String fullUrl = baseUrl + url + "/me";
		return webClientBuilder.build()
            .get()
            .uri(URI.create(fullUrl))
            .retrieve()
            .toEntity(String.class);
	}
}

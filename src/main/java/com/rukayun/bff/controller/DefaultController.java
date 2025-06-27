package com.rukayun.bff.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
public class DefaultController {

	@GetMapping
	public String Get() {

		System.out.println("GET request");
		return "Hello, World";
	}


	@GetMapping("claims")
	public ResponseEntity<?> getClaims(@AuthenticationPrincipal Jwt jwt) {
		return ResponseEntity.ok(jwt.getClaims());
	}
	
}
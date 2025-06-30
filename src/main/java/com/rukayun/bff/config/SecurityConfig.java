package com.rukayun.bff.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> 
                authorize
                    .requestMatchers("/").permitAll()
                    .requestMatchers("/comunas").permitAll()
                    .requestMatchers(HttpMethod.GET, "/animales/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/organizaciones/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/adopciones/estados").permitAll()
                    .requestMatchers(HttpMethod.GET, "/seguimientos/estados").permitAll()
                    .requestMatchers(HttpMethod.GET, "/seguimientos/tipos").permitAll()
                    .anyRequest().authenticated()
            )
			.oauth2ResourceServer((oauth2) -> 
                oauth2.jwt(Customizer.withDefaults())
            );
		return http.build();
	}

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedOrigin("https://main.d1olb5fn4vfyih.amplifyapp.com");
        
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
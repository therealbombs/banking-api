package com.ox.banking.config;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
//
//    @Bean
//    public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
//        return new KeycloakSpringBootConfigResolver();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeHttpRequests()
//            .requestMatchers("/api/**").authenticated()
//            .anyRequest().permitAll();
//        return http.build();
//    }
	
	    
	    @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	            .csrf().disable()
	            .authorizeHttpRequests()
	            .anyRequest().permitAll();  // Permette tutte le richieste senza auth
	        return http.build();
	    }
	
}
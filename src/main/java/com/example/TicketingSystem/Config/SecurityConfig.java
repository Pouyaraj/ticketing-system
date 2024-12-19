package com.example.TicketingSystem.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() 
            .authorizeRequests()
                .requestMatchers("/register", "/login", "/tickets/submit", "/tickets/get-tickets").permitAll() // Allow unauthenticated access to these endpoints
                .anyRequest().authenticated() // Require authentication for all other endpoints
            .and()
            .httpBasic(); 

        return http.build();
    }
}


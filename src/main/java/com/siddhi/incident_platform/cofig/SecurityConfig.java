package com.siddhi.incident_platform.cofig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig
{
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http.csrf(csrf -> csrf.disable());
        http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/api/health").permitAll();
                    auth.requestMatchers("/api/incidents/**").permitAll();
                    auth.anyRequest().authenticated();
                });

        return http.build();
    }
}

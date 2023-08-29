package br.com.amparo.backend.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

@EnableWebSecurity
@Configuration
public class AmparoSecurityConfiguration {
    private static final String[] ALLOWED_ENDPOINTS = {"/actuator/**", "/login"};
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                .requestMatchers(ALLOWED_ENDPOINTS); //Will ignore security on allowed endpoints
    }
}
package com.example.agrofood_backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Todos los endpoints
                //.allowedOrigins("http://localhost:5173") // Tu frontend de Vite
                .allowedOrigins("https://agrofood-production.up.railway.app")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD", "PATCH")
                .allowedHeaders("*") // Todos los headers
                .allowCredentials(true) // Permitir cookies/autenticación
                .maxAge(3600); // Cache preflight por 1 hora
    }
}

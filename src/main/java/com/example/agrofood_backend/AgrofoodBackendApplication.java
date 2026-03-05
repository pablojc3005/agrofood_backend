package com.example.agrofood_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AgrofoodBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AgrofoodBackendApplication.class, args);
        System.out.println("🚀 Aplicación iniciada correctamente!");
        System.out.println("📊 Conectando a MariaDB...");
	}
}

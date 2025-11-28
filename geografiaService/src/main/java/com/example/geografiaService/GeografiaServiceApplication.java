package com.example.geografiaService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GeografiaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeografiaServiceApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner printSwaggerUrl() {
		return args -> {
			System.out.println("=================================================");
			System.out.println("Swagger UI: http://localhost:8083/swagger-ui.html");
			System.out.println("=================================================");
		};
	}
}

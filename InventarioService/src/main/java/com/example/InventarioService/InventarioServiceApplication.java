package com.example.InventarioService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventarioServiceApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner printSwaggerUrl() {
		return args -> {
			System.out.println("=================================================");
			System.out.println("Swagger UI: http://localhost:8082/swagger-ui.html");
			System.out.println("=================================================");
		};
	}
}

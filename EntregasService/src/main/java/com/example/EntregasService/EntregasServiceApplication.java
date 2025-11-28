package com.example.EntregasService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EntregasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EntregasServiceApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner printSwaggerUrl() {
		return args -> {
			System.out.println("=================================================");
			System.out.println("Swagger UI: http://localhost:8084/swagger-ui.html");
			System.out.println("=================================================");
		};
	}
}

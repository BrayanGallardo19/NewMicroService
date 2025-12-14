package com.example.VentasService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VentasServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(VentasServiceApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner printSwaggerUrl() {
		return args -> {
			System.out.println("=================================================");
			System.out.println("Swagger UI: http://localhost:8085/swagger-ui/index.html");
			System.out.println("=================================================");
		};
	}
}

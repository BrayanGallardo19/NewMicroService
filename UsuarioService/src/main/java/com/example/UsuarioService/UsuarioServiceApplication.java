package com.example.UsuarioService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UsuarioServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsuarioServiceApplication.class, args);
	}

	@org.springframework.context.annotation.Bean
	public org.springframework.boot.CommandLineRunner printSwaggerUrl() {
		return args -> {
			System.out.println("=================================================");
			System.out.println("Swagger UI: http://localhost:8081/swagger-ui/index.html");
			System.out.println("=================================================");
		};
	}
}

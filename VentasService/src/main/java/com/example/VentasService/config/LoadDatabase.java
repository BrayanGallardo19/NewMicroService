package com.example.VentasService.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            log.info("=================================================");
            log.info("VentasService - Base de datos inicializada");
            log.info("Las ventas se crearán dinámicamente cuando los usuarios realicen compras");
            log.info("=================================================");
        };
    }
}

package com.example.geografiaService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // Allow credentials
        config.setAllowCredentials(true);

        // Allow all origins (for development with DevTunnels and localhost)
        config.setAllowedOriginPatterns(Arrays.asList("*"));

        // Allow all headers
        config.addAllowedHeader("*");

        // Allow all HTTP methods
        config.addAllowedMethod("*");

        // Expose headers
        config.setExposedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "Accept",
                "X-Requested-With",
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials"));

        // Max age for preflight cache
        config.setMaxAge(3600L);

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}

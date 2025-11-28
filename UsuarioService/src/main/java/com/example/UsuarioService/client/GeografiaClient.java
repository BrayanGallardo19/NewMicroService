package com.example.UsuarioService.client;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GeografiaClient {

    private final WebClient webClient;

    public GeografiaClient(@Value("${geografia.service.url:http://localhost:8083}") String geografiaServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(geografiaServiceUrl)
                .build();
    }

    public ComunaDTO getComunaById(Integer idComuna) {
        try {
            return webClient.get()
                    .uri("/api/v1/comunas/{id}", idComuna)
                    .retrieve()
                    .bodyToMono(ComunaDTO.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    public RegionDTO getRegionById(Integer idRegion) {
        try {
            return webClient.get()
                    .uri("/api/v1/regiones/{id}", idRegion)
                    .retrieve()
                    .bodyToMono(RegionDTO.class)
                    .block();
        } catch (Exception e) {
            return null;
        }
    }

    @Data
    public static class ComunaDTO {
        private Integer idComuna;
        private String nombreComuna;
        private RegionDTO region;
    }

    @Data
    public static class RegionDTO {
        private Integer idRegion;
        private String nombreRegion;
        private String codigoRegion;
    }
}

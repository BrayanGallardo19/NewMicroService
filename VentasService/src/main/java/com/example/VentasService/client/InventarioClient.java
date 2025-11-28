package com.example.VentasService.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Component
public class InventarioClient {

    private final WebClient webClient;

    public InventarioClient(WebClient.Builder webClientBuilder,
            @Value("${inventario.service.url}") String inventarioServiceUrl) {
        this.webClient = webClientBuilder.baseUrl(inventarioServiceUrl).build();
    }

    public void reservarStock(Integer idInventario, Integer cantidad) {
        Map<String, Object> request = new HashMap<>();
        request.put("cantidad", cantidad);
        request.put("usuarioResponsable", 1); // Usuario sistema por defecto
        request.put("motivo", "Reserva por venta");

        try {
            webClient.post()
                    .uri("/api/inventario/" + idInventario + "/reservar")
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block(); // Bloqueamos para asegurar que se reserve antes de continuar
            System.out.println("Stock reservado correctamente para inventario ID: " + idInventario);
        } catch (Exception e) {
            System.err.println("Error al reservar stock: " + e.getMessage());
            // Podríamos lanzar una excepción aquí si queremos que falle la venta si no hay
            // stock
            throw new RuntimeException("Error al reservar stock: " + e.getMessage());
        }
    }
}

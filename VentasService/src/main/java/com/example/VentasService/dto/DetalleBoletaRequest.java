package com.example.VentasService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleBoletaRequest {
    private BoletaIdDto boleta;
    private InventarioIdDto inventario;
    private Integer cantidad;
    private Integer precioUnitario;
    private Integer subtotal;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BoletaIdDto {
        private Integer idBoleta;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InventarioIdDto {
        private Integer idInventario;
    }
}

package com.example.VentasService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para crear un detalle de boleta")
public class DetalleBoletaRequest {
    
    @Schema(description = "Boleta asociada", requiredMode = Schema.RequiredMode.REQUIRED)
    private BoletaIdDto boleta;
    
    @Schema(description = "Inventario del producto", requiredMode = Schema.RequiredMode.REQUIRED)
    private InventarioIdDto inventario;
    
    @Schema(description = "Cantidad de unidades", example = "2", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "1")
    private Integer cantidad;
    
    @Schema(description = "Precio unitario en pesos chilenos", example = "44995", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    private Integer precioUnitario;
    
    @Schema(description = "Subtotal (cantidad × precio unitario)", example = "89990", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    private Integer subtotal;
}

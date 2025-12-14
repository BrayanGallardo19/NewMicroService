package com.example.VentasService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO con el ID de un inventario")
public class InventarioIdDto {
    
    @Schema(description = "ID del inventario", example = "25", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idInventario;
}

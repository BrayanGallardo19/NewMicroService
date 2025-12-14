package com.example.VentasService.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO con el ID de una boleta")
public class BoletaIdDto {
    
    @Schema(description = "ID de la boleta", example = "1", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idBoleta;
}

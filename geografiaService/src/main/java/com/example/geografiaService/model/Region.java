package com.example.geografiaService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "region")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Región de Chile")
public class Region {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    @Schema(description = "ID único de la región", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idRegion;
    
    @NotBlank(message = "El nombre de la región es obligatorio")
    @Column(name = "nombre_region", unique = true, nullable = false, length = 100)
    @Schema(description = "Nombre de la región", example = "Región Metropolitana", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreRegion;
    
    @Column(name = "codigo_region", length = 10)
    @Schema(description = "Código de la región", example = "RM")
    private String codigoRegion;
}

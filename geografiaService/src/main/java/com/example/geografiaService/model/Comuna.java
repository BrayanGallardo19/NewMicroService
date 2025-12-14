package com.example.geografiaService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comuna")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Comuna perteneciente a una región")
public class Comuna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comuna")
    @Schema(description = "ID único de la comuna", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idComuna;
    
    @NotBlank(message = "El nombre de la comuna es obligatorio")
    @Column(name = "nombre_comuna", nullable = false, length = 100)
    @Schema(description = "Nombre de la comuna", example = "Santiago", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreComuna;
    
    @NotNull(message = "La región es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_region", nullable = false)
    @Schema(description = "Región a la que pertenece", requiredMode = Schema.RequiredMode.REQUIRED)
    private Region region;
}

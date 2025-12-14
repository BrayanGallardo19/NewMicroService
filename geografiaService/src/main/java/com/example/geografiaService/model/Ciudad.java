package com.example.geografiaService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ciudad")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Ciudad perteneciente a una comuna")
public class Ciudad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciudad")
    @Schema(description = "ID único de la ciudad", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idCiudad;
    
    @NotBlank(message = "El nombre de la ciudad es obligatorio")
    @Column(name = "nombre_ciudad", nullable = false, length = 100)
    @Schema(description = "Nombre de la ciudad", example = "Providencia", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreCiudad;
    
    @NotNull(message = "La comuna es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_comuna", nullable = false)
    @Schema(description = "Comuna a la que pertenece", requiredMode = Schema.RequiredMode.REQUIRED)
    private Comuna comuna;
}

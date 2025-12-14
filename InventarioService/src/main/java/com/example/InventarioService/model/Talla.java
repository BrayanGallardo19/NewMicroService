package com.example.InventarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "talla")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Tallas disponibles para los zapatos")
public class Talla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_talla")
    @Schema(description = "ID único de la talla", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idTalla;

    @NotBlank(message = "El número de talla es obligatorio")
    @Column(name = "numero_talla", unique = true, nullable = false, length = 10)
    @Schema(description = "Número de la talla", example = "42", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numeroTalla;
}

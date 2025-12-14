package com.example.InventarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "marca")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Marca de zapatos")
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca")
    @Schema(description = "ID único de la marca", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idMarca;

    @NotBlank(message = "El nombre de la marca es obligatorio")
    @Column(name = "nombre_marca", unique = true, nullable = false, length = 100)
    @Schema(description = "Nombre de la marca", example = "Nike", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombreMarca;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Schema(description = "Descripción de la marca", example = "Marca líder en calzado deportivo")
    private String descripcion;

    @Column(name = "estado", length = 20, nullable = false)
    @Schema(description = "Estado de la marca", example = "activa", allowableValues = {"activa", "inactiva"})
    private String estado = "activa";
}

package com.example.InventarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "modelozapato")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Modelo de zapato")
public class ModeloZapato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    @Schema(description = "ID del modelo", example = "1")
    private Integer idModelo;

    @NotNull(message = "La marca es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_marca", nullable = false)
    @Schema(description = "Marca del zapato")
    private Marca marca;

    @NotBlank(message = "El nombre del modelo es obligatorio")
    @Column(name = "nombre_modelo", nullable = false, length = 150)
    @Schema(description = "Nombre del modelo", example = "Air Max 90")
    private String nombreModelo;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    @Schema(description = "Descripción del modelo", example = "Zapatillas deportivas clásicas con tecnología Air")
    private String descripcion;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio debe ser mayor o igual a 0")
    @Column(name = "precio_unitario", nullable = false)
    @Schema(description = "Precio en pesos chilenos", example = "89990")
    private Integer precioUnitario;

    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    @Schema(description = "Imagen del modelo en bytes", hidden = true)
    private byte[] imagen;

    @Column(name = "estado", length = 20, nullable = false)
    @Schema(description = "Estado del modelo", example = "activo", allowableValues = {"activo", "inactivo"})
    private String estado = "activo";

    @Column(name = "categoria", length = 50)
    @Schema(description = "Categoría del zapato", example = "deportivos", allowableValues = {"hombre", "mujer", "niños", "deportivos"})
    private String categoria;
}

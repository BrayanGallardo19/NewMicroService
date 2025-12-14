package com.example.InventarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "id_modelo", "id_talla" })
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Inventario de zapatos por modelo y talla")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    @Schema(description = "ID único del inventario", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idInventario;

    @NotNull(message = "El modelo es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_modelo", nullable = false)
    @Schema(description = "Modelo de zapato", requiredMode = Schema.RequiredMode.REQUIRED)
    private ModeloZapato modelo;

    @NotNull(message = "La talla es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_talla", nullable = false)
    @Schema(description = "Talla del zapato", requiredMode = Schema.RequiredMode.REQUIRED)
    private Talla talla;

    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock_actual", nullable = false)
    @Schema(description = "Cantidad disponible en inventario", example = "50", minimum = "0")
    private Integer stockActual = 0;
}

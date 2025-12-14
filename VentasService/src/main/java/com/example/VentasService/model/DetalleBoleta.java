package com.example.VentasService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalleboleta")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Detalle de cada producto en una boleta de venta")
public class DetalleBoleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    @Schema(description = "ID único del detalle", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idDetalle;

    @NotNull(message = "La boleta es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boleta", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Boleta a la que pertenece el detalle", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boleta boleta;

    @NotNull(message = "El ID del inventario es obligatorio")
    @Column(name = "id_inventario", nullable = false)
    @Schema(description = "ID del producto en inventario", example = "25", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idInventario;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(name = "cantidad", nullable = false)
    @Schema(description = "Cantidad de unidades vendidas", example = "2", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "1")
    private Integer cantidad;

    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio unitario debe ser mayor o igual a 0")
    @Column(name = "precio_unitario", nullable = false)
    @Schema(description = "Precio unitario del producto en pesos chilenos", example = "44995", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    private Integer precioUnitario;

    @NotNull(message = "El subtotal es obligatorio")
    @Min(value = 0, message = "El subtotal debe ser mayor o igual a 0")
    @Column(name = "subtotal", nullable = false)
    @Schema(description = "Subtotal (cantidad × precio unitario)", example = "89990", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    private Integer subtotal;
}

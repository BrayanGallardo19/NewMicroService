package com.example.InventarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "movimientoinventario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Registro de movimientos de inventario (entradas, salidas, ajustes)")
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimiento")
    @Schema(description = "ID único del movimiento", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idMovimiento;

    @NotNull(message = "El inventario es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_inventario", nullable = false)
    @Schema(description = "Inventario afectado por el movimiento", requiredMode = Schema.RequiredMode.REQUIRED)
    private Inventario inventario;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Column(name = "tipo_movimiento", nullable = false, length = 50)
    @Schema(description = "Tipo de movimiento", example = "ENTRADA", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"ENTRADA", "SALIDA", "AJUSTE", "RESERVA", "LIBERACION"})
    private String tipoMovimiento; // ENTRADA, SALIDA, AJUSTE, RESERVA, LIBERACION

    @NotNull(message = "La cantidad es obligatoria")
    @Column(name = "cantidad", nullable = false)
    @Schema(description = "Cantidad de unidades en el movimiento", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cantidad;

    @Column(name = "stock_anterior", nullable = false)
    @Schema(description = "Stock antes del movimiento", example = "50", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer stockAnterior;

    @Column(name = "stock_nuevo", nullable = false)
    @Schema(description = "Stock después del movimiento", example = "60", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer stockNuevo;

    @Column(name = "fecha_movimiento", nullable = false)
    @Schema(description = "Fecha y hora del movimiento", example = "2025-12-13T20:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaMovimiento = LocalDateTime.now();

    @Column(name = "motivo", columnDefinition = "TEXT")
    @Schema(description = "Motivo del movimiento", example = "Recepción de nueva mercadería")
    private String motivo;

    @Column(name = "usuario_responsable")
    @Schema(description = "ID del usuario responsable del movimiento", example = "1")
    private Integer usuarioResponsable;
}

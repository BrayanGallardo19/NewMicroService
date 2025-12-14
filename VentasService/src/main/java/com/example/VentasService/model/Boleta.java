package com.example.VentasService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "boletaventa")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Boleta de venta del sistema")
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleta")
    @Schema(description = "ID único de la boleta", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idBoleta;

    @NotBlank(message = "El número de boleta es obligatorio")
    @Column(name = "numero_boleta", unique = true, nullable = false, length = 50)
    @Schema(description = "Número de boleta", example = "B00001", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numeroBoleta;

    @Column(name = "fecha", nullable = false)
    @Schema(description = "Fecha y hora de la venta", example = "2025-12-13T20:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "id_vendedor")
    @Schema(description = "ID del vendedor", example = "5")
    private Integer idVendedor;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Column(name = "id_cliente", nullable = false)
    @Schema(description = "ID del cliente", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idCliente;

    @NotNull(message = "El monto total es obligatorio")
    @Min(value = 0, message = "El monto total debe ser mayor o igual a 0")
    @Column(name = "monto_total", nullable = false)
    @Schema(description = "Monto total de la venta en pesos chilenos", example = "89990", requiredMode = Schema.RequiredMode.REQUIRED, minimum = "0")
    private Integer montoTotal;

    @Column(name = "estado", length = 20, nullable = false)
    @Schema(description = "Estado de la boleta", example = "COMPLETADA", allowableValues = {"COMPLETADA", "CANCELADA", "PENDIENTE"})
    private String estado = "COMPLETADA"; // COMPLETADA, CANCELADA, PENDIENTE
}

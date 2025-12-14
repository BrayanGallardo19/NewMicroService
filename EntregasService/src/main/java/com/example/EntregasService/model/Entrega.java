package com.example.EntregasService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "entrega")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entrega asociada a una boleta de venta")
public class Entrega {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega")
    @Schema(description = "ID único de la entrega", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idEntrega;
    
    @NotNull(message = "El ID de la boleta es obligatorio")
    @Column(name = "id_boleta", nullable = false)
    @Schema(description = "ID de la boleta asociada", example = "15", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer idBoleta;
    
    @Column(name = "id_transportista")
    @Schema(description = "ID del transportista asignado", example = "8")
    private Integer idTransportista;
    
    @Column(name = "estado_entrega", length = 50, nullable = false)
    @Schema(description = "Estado actual de la entrega", example = "pendiente", allowableValues = {"pendiente", "asignada", "en_camino", "entregada", "cancelada"})
    private String estadoEntrega = "pendiente"; // pendiente, asignada, en_camino, entregada, cancelada
    
    @Column(name = "fecha_asignacion")
    @Schema(description = "Fecha y hora de asignación al transportista", example = "2025-12-13T20:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaAsignacion = LocalDateTime.now();
    
    @Column(name = "fecha_entrega")
    @Schema(description = "Fecha y hora en que se completó la entrega", example = "2025-12-14T15:30:00")
    private LocalDateTime fechaEntrega;
    
    @Column(name = "observacion", columnDefinition = "TEXT")
    @Schema(description = "Observaciones sobre la entrega", example = "Cliente solicitó entrega por la tarde")
    private String observacion;
    
    @Column(name = "direccion_entrega", columnDefinition = "TEXT")
    @Schema(description = "Dirección completa de entrega", example = "Av. Providencia 123, Depto 405")
    private String direccionEntrega;
    
    @Column(name = "id_comuna")
    @Schema(description = "ID de la comuna de entrega", example = "5")
    private Integer idComuna;
}

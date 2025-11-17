package com.example.VentasService.entity;

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
public class DetalleBoleta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle")
    private Integer idDetalle;
    
    @NotNull(message = "La boleta es obligatoria")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boleta", nullable = false)
    private Boleta boleta;
    
    @NotNull(message = "El ID del inventario es obligatorio")
    @Column(name = "id_inventario", nullable = false)
    private Integer idInventario;
    
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser mayor a 0")
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    
    @NotNull(message = "El precio unitario es obligatorio")
    @Min(value = 0, message = "El precio unitario debe ser mayor o igual a 0")
    @Column(name = "precio_unitario", nullable = false)
    private Integer precioUnitario;
    
    @NotNull(message = "El subtotal es obligatorio")
    @Min(value = 0, message = "El subtotal debe ser mayor o igual a 0")
    @Column(name = "subtotal", nullable = false)
    private Integer subtotal;
}

package com.example.InventarioService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inventario", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id_modelo", "id_talla"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inventario")
    private Integer idInventario;
    
    @NotNull(message = "El modelo es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_modelo", nullable = false)
    private ModeloZapato modelo;
    
    @NotNull(message = "La talla es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_talla", nullable = false)
    private Talla talla;
    
    @Min(value = 0, message = "El stock no puede ser negativo")
    @Column(name = "stock_actual", nullable = false)
    private Integer stockActual = 0;
}

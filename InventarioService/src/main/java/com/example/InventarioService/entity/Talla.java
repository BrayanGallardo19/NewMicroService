package com.example.InventarioService.entity;

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
public class Talla {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_talla")
    private Integer idTalla;
    
    @NotBlank(message = "El número de talla es obligatorio")
    @Column(name = "numero_talla", unique = true, nullable = false, length = 10)
    private String numeroTalla;
}

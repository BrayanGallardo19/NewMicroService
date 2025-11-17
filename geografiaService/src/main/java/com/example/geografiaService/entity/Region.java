package com.example.geografiaService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "region")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Region {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_region")
    private Integer idRegion;
    
    @NotBlank(message = "El nombre de la región es obligatorio")
    @Column(name = "nombre_region", unique = true, nullable = false, length = 100)
    private String nombreRegion;
    
    @Column(name = "codigo_region", length = 10)
    private String codigoRegion;
}

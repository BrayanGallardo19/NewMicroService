package com.example.geografiaService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "comuna")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comuna {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comuna")
    private Integer idComuna;
    
    @NotBlank(message = "El nombre de la comuna es obligatorio")
    @Column(name = "nombre_comuna", nullable = false, length = 100)
    private String nombreComuna;
    
    @NotNull(message = "La región es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_region", nullable = false)
    private Region region;
}

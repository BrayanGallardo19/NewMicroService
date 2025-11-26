package com.example.geografiaService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ciudad")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ciudad {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciudad")
    private Integer idCiudad;
    
    @NotBlank(message = "El nombre de la ciudad es obligatorio")
    @Column(name = "nombre_ciudad", nullable = false, length = 100)
    private String nombreCiudad;
    
    @NotNull(message = "La comuna es obligatoria")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_comuna", nullable = false)
    private Comuna comuna;
}

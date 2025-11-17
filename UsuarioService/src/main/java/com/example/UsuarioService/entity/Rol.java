package com.example.UsuarioService.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rol")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rol {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    private Integer idRol;
    
    @NotBlank(message = "El nombre del rol es obligatorio")
    @Column(name = "nombre_rol", unique = true, nullable = false, length = 50)
    private String nombreRol;
    
    @Column(name = "descripcion", length = 255)
    private String descripcion;
}

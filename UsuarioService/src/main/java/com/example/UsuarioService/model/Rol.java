package com.example.UsuarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Roles disponibles en el sistema")
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol")
    @Schema(description = "ID único del rol", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idRol;

    @NotBlank(message = "El nombre del rol es obligatorio")
    @Column(name = "nombre_rol", unique = true, nullable = false, length = 50)
    @Schema(description = "Nombre del rol", example = "ADMIN", requiredMode = Schema.RequiredMode.REQUIRED, allowableValues = {"ADMIN", "CLIENTE", "VENDEDOR", "TRANSPORTISTA"})
    private String nombreRol;

    @Column(name = "descripcion", length = 255)
    @Schema(description = "Descripción del rol", example = "Administrador del sistema")
    private String descripcion;
}

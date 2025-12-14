package com.example.UsuarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Usuario del sistema con su rol asignado")
public class Usuario {

    @Id
    @Column(name = "id_persona")
    @Schema(description = "ID del usuario (mismo que el ID de persona)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idPersona;

    @NotNull(message = "La persona es obligatoria")
    @OneToOne(fetch = FetchType.EAGER)
    @MapsId
    @JoinColumn(name = "id_persona")
    @Schema(description = "Datos personales del usuario", requiredMode = Schema.RequiredMode.REQUIRED)
    private Persona persona;

    @NotNull(message = "El rol es obligatorio")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_rol", nullable = false)
    @Schema(description = "Rol asignado al usuario", requiredMode = Schema.RequiredMode.REQUIRED)
    private Rol rol;
}

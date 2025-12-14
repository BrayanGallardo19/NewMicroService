package com.example.UsuarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "persona")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Entidad que representa a una persona en el sistema")
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    @Schema(description = "ID único de la persona", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idPersona;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false, length = 100)
    @Schema(description = "Nombre de la persona", example = "Juan", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "apellido", nullable = false, length = 100)
    @Schema(description = "Apellido de la persona", example = "Pérez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String apellido;

    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]{1}$", message = "Formato de RUT inválido (ej: 12345678-9)")
    @Column(name = "rut", unique = true, nullable = false, length = 12)
    @Schema(description = "RUT chileno de la persona", example = "12345678-9", requiredMode = Schema.RequiredMode.REQUIRED)
    private String rut;

    @Column(name = "telefono", length = 20)
    @Schema(description = "Número de teléfono", example = "+56912345678")
    private String telefono;

    @Email(message = "El email no es válido")
    @Column(name = "email", length = 100)
    @Schema(description = "Correo electrónico", example = "juan.perez@example.com")
    private String email;

    @Column(name = "id_comuna")
    @Schema(description = "ID de la comuna donde reside", example = "1")
    private Integer idComuna;

    @Column(name = "calle", length = 200)
    @Schema(description = "Nombre de la calle", example = "Avenida Providencia")
    private String calle;

    @Column(name = "numero_puerta", length = 10)
    @Schema(description = "Número de puerta o departamento", example = "123")
    private String numeroPuerta;

    @NotBlank(message = "El username es obligatorio")
    @Column(name = "username", unique = true, nullable = false, length = 50)
    @Schema(description = "Nombre de usuario único", example = "juanperez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "password_hash", nullable = false)
    @Schema(description = "Contraseña encriptada", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED, accessMode = Schema.AccessMode.WRITE_ONLY)
    private String passHash;

    @Column(name = "fecha_registro", nullable = false)
    @Schema(description = "Fecha y hora de registro", example = "2025-12-13T20:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "genero", length = 20)
    @Schema(description = "Género de la persona", example = "Masculino")
    private String genero;

    @Column(name = "fecha_nacimiento")
    @Schema(description = "Fecha de nacimiento", example = "1990-01-15")
    private java.time.LocalDate fechaNacimiento;

    @Column(name = "estado", length = 20, nullable = false)
    @Schema(description = "Estado de la cuenta", example = "activo", allowableValues = {"activo", "inactivo"})
    private String estado = "activo";
}

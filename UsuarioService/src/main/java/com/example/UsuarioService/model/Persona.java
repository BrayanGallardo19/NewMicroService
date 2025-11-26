package com.example.UsuarioService.model;

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
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private Integer idPersona;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;

    @NotBlank(message = "El RUT es obligatorio")
    @Pattern(regexp = "^[0-9]{7,8}-[0-9Kk]{1}$", message = "Formato de RUT inválido (ej: 12345678-9)")
    @Column(name = "rut", unique = true, nullable = false, length = 12)
    private String rut;

    @Column(name = "telefono", length = 20)
    private String telefono;

    @Email(message = "El email no es válido")
    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "id_comuna")
    private Integer idComuna;

    @Column(name = "calle", length = 200)
    private String calle;

    @Column(name = "numero_puerta", length = 10)
    private String numeroPuerta;

    @NotBlank(message = "El username es obligatorio")
    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(name = "password_hash", nullable = false)
    private String passHash;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "estado", length = 20, nullable = false)
    private String estado = "activo";
}

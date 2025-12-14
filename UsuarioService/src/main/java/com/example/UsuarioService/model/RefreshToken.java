package com.example.UsuarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
@Schema(description = "Token de refresco para mantener sesiones activas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del refresh token", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;

    @Column(nullable = false, unique = true)
    @Schema(description = "Token de refresco único", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Column(nullable = false)
    @Schema(description = "Fecha de expiración del token", example = "2025-12-20T20:00:00Z")
    private Instant expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_persona", unique = true)
    @Schema(description = "Usuario al que pertenece el token")
    private Usuario user;
}

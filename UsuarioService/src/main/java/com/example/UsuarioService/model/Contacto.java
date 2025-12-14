package com.example.UsuarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "contactos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Mensajes de contacto enviados por usuarios")
public class Contacto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del contacto", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Column(nullable = false)
    @Schema(description = "Nombre de quien envía el mensaje", example = "María González", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @Column(nullable = false)
    @Schema(description = "Correo electrónico de contacto", example = "maria@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String correo;

    @Column(nullable = false, columnDefinition = "TEXT")
    @Schema(description = "Comentario o mensaje", example = "Me gustaría obtener más información sobre...", requiredMode = Schema.RequiredMode.REQUIRED)
    private String comentario;

    @Schema(description = "Fecha y hora del mensaje", example = "2025-12-13T20:00:00", accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fecha;

    @PrePersist
    protected void onCreate() {
        fecha = LocalDateTime.now();
    }
}

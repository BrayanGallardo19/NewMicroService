package com.example.UsuarioService.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Cliente con información adicional de categorización")
public class Cliente {

    @Id
    @Column(name = "id_persona")
    @Schema(description = "ID del cliente (mismo que el ID de persona)", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer idPersona;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id_persona")
    @Schema(description = "Datos personales del cliente")
    private Persona persona;

    @Column(name = "categoria", length = 50)
    @Schema(description = "Categoría del cliente", example = "VIP", allowableValues = {"Regular", "VIP", "Premium"})
    private String categoria;
}

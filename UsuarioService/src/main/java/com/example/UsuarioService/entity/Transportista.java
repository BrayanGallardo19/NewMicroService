package com.example.UsuarioService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transportista")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transportista {
    
    @Id
    @Column(name = "id_persona")
    private Integer idPersona;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = "id_persona")
    private Persona persona;
    
    @Column(name = "patente_vehiculo", length = 20)
    private String patenteVehiculo;
    
    @Column(name = "tipo_vehiculo", length = 50)
    private String tipoVehiculo;
}

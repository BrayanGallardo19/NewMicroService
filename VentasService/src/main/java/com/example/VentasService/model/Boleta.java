package com.example.VentasService.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "boletaventa")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Boleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_boleta")
    private Integer idBoleta;

    @NotBlank(message = "El número de boleta es obligatorio")
    @Column(name = "numero_boleta", unique = true, nullable = false, length = 50)
    private String numeroBoleta;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha = LocalDateTime.now();

    @Column(name = "id_vendedor")
    private Integer idVendedor;

    @NotNull(message = "El ID del cliente es obligatorio")
    @Column(name = "id_cliente", nullable = false)
    private Integer idCliente;

    @NotNull(message = "El monto total es obligatorio")
    @Min(value = 0, message = "El monto total debe ser mayor o igual a 0")
    @Column(name = "monto_total", nullable = false)
    private Integer montoTotal;

    @Column(name = "estado", length = 20, nullable = false)
    private String estado = "COMPLETADA"; // COMPLETADA, CANCELADA, PENDIENTE
}

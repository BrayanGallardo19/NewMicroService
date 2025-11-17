package com.example.VentasService.repository;

import com.example.VentasService.entity.DetalleBoleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleBoletaRepository extends JpaRepository<DetalleBoleta, Integer> {
    List<DetalleBoleta> findByBoleta_IdBoleta(Integer idBoleta);
}

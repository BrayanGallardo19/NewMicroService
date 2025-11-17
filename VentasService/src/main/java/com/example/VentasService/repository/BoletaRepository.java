package com.example.VentasService.repository;

import com.example.VentasService.entity.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Integer> {
    Optional<Boleta> findByNumeroBoleta(String numeroBoleta);
    List<Boleta> findByIdCliente(Integer idCliente);
    List<Boleta> findByIdVendedor(Integer idVendedor);
    List<Boleta> findByEstado(String estado);
    List<Boleta> findByFechaBetween(LocalDateTime inicio, LocalDateTime fin);
}

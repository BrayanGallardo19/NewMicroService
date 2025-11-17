package com.example.EntregasService.repository;

import com.example.EntregasService.entity.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Integer> {
    Optional<Entrega> findByIdBoleta(Integer idBoleta);
    List<Entrega> findByIdTransportista(Integer idTransportista);
    List<Entrega> findByEstadoEntrega(String estadoEntrega);
    List<Entrega> findByIdTransportistaAndEstadoEntrega(Integer idTransportista, String estadoEntrega);
}

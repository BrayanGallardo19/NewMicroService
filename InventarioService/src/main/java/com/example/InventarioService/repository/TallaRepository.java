package com.example.InventarioService.repository;

import com.example.InventarioService.entity.Talla;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TallaRepository extends JpaRepository<Talla, Integer> {
    Optional<Talla> findByNumeroTalla(String numeroTalla);
    boolean existsByNumeroTalla(String numeroTalla);
}

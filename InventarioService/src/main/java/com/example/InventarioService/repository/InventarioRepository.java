package com.example.InventarioService.repository;

import com.example.InventarioService.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventarioRepository extends JpaRepository<Inventario, Integer> {
    Optional<Inventario> findByModelo_IdModeloAndTalla_IdTalla(Integer idModelo, Integer idTalla);

    List<Inventario> findByModelo_IdModelo(Integer idModelo);

    @Query("SELECT i FROM Inventario i WHERE i.stockActual < :minStock")
    List<Inventario> findStockBajo(Integer minStock);

    @Query("SELECT i FROM Inventario i WHERE i.stockActual = 0")
    List<Inventario> findSinStock();
}

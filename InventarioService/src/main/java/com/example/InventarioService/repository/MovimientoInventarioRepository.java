package com.example.InventarioService.repository;

import com.example.InventarioService.entity.MovimientoInventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimientoInventarioRepository extends JpaRepository<MovimientoInventario, Integer> {
    List<MovimientoInventario> findByInventario_IdInventario(Integer idInventario);
    List<MovimientoInventario> findByTipoMovimiento(String tipoMovimiento);
    List<MovimientoInventario> findByFechaMovimientoBetween(LocalDateTime inicio, LocalDateTime fin);
}

package com.example.InventarioService.repository;

import com.example.InventarioService.model.ModeloZapato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloZapatoRepository extends JpaRepository<ModeloZapato, Integer> {
    List<ModeloZapato> findByMarca_IdMarca(Integer idMarca);

    List<ModeloZapato> findByEstado(String estado);

    List<ModeloZapato> findByMarca_IdMarcaAndEstado(Integer idMarca, String estado);

    java.util.Optional<ModeloZapato> findByNombreModelo(String nombreModelo);
}

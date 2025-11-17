package com.example.geografiaService.repository;

import com.example.geografiaService.entity.Comuna;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComunaRepository extends JpaRepository<Comuna, Integer> {
    List<Comuna> findByRegion_IdRegion(Integer idRegion);
    boolean existsByNombreComunaAndRegion_IdRegion(String nombreComuna, Integer idRegion);
}

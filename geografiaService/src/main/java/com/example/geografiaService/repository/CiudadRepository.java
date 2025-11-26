package com.example.geografiaService.repository;

import com.example.geografiaService.model.Ciudad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {
    List<Ciudad> findByComuna_IdComuna(Integer idComuna);
}

package com.example.UsuarioService.repository;

import com.example.UsuarioService.model.Transportista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransportistaRepository extends JpaRepository<Transportista, Integer> {
    List<Transportista> findByPersona_Estado(String estado);
}

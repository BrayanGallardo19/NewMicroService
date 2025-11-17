package com.example.UsuarioService.repository;

import com.example.UsuarioService.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Integer> {
    
    // Buscar persona por RUT
    Optional<Persona> findByRut(String rut);
    
    // Buscar persona por email
    Optional<Persona> findByEmail(String email);
    
    // Buscar persona por username
    Optional<Persona> findByUsername(String username);
    
    // Verificar si existe un RUT
    boolean existsByRut(String rut);
    
    // Verificar si existe un email
    boolean existsByEmail(String email);
    
    // Verificar si existe un username
    boolean existsByUsername(String username);
}

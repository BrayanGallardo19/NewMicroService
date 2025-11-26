package com.example.UsuarioService.repository;

import com.example.UsuarioService.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByPersona_Username(String username);

    boolean existsByPersona_Username(String username);

    Optional<Usuario> findByPersona_Email(String email);
}

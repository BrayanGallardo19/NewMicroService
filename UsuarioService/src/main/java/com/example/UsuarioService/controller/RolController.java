package com.example.UsuarioService.controller;

import org.springframework.web.bind.annotation.*;
import com.example.UsuarioService.model.Rol;
import com.example.UsuarioService.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@CrossOrigin(origins = "*")
public class RolController {

    @Autowired
    private RolRepository rolRepository;

    @GetMapping
    public ResponseEntity<List<Rol>> obtenerTodos() {
        return ResponseEntity.ok(rolRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rol> obtenerPorId(@PathVariable Integer id) {
        return rolRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Rol> crear(@RequestBody Rol rol) {
        Rol nuevoRol = rolRepository.save(rol);
        return ResponseEntity.ok(nuevoRol);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Rol> actualizar(@PathVariable Integer id, @RequestBody Rol rol) {
        if (!rolRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rol.setIdRol(id);
        Rol actualizado = rolRepository.save(rol);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!rolRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        rolRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

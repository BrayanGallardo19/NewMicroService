package com.example.UsuarioService.controller;

import org.springframework.web.bind.annotation.*;
import com.example.UsuarioService.entity.Persona;
import com.example.UsuarioService.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/personas")
@CrossOrigin(origins = "*")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    @GetMapping
    public ResponseEntity<List<Persona>> obtenerTodas() {
        return ResponseEntity.ok(personaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Persona> obtenerPorId(@PathVariable Integer id) {
        return personaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Persona> crear(@RequestBody Persona persona) {
        Persona nuevaPersona = personaRepository.save(persona);
        return ResponseEntity.ok(nuevaPersona);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Persona> actualizar(@PathVariable Integer id, @RequestBody Persona persona) {
        if (!personaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        persona.setIdPersona(id);
        Persona actualizada = personaRepository.save(persona);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!personaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        personaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

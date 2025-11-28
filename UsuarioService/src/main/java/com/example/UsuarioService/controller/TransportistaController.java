package com.example.UsuarioService.controller;

import org.springframework.web.bind.annotation.*;
import com.example.UsuarioService.model.Transportista;
import com.example.UsuarioService.repository.TransportistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transportistas")
@CrossOrigin(origins = "*")
public class TransportistaController {

    @Autowired
    private TransportistaRepository transportistaRepository;

    @GetMapping
    public ResponseEntity<List<Transportista>> obtenerTodos() {
        return ResponseEntity.ok(transportistaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transportista> obtenerPorId(@PathVariable Integer id) {
        return transportistaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Transportista> crear(@RequestBody Transportista transportista) {
        Transportista nuevoTransportista = transportistaRepository.save(transportista);
        return ResponseEntity.ok(nuevoTransportista);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transportista> actualizar(@PathVariable Integer id,
            @RequestBody Transportista transportista) {
        if (!transportistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        transportista.setIdPersona(id);
        Transportista actualizado = transportistaRepository.save(transportista);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!transportistaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        transportistaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

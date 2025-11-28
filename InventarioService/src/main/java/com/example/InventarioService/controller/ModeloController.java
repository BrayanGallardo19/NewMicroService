package com.example.InventarioService.controller;

import com.example.InventarioService.model.ModeloZapato;
import com.example.InventarioService.service.ModeloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/modelos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ModeloController {

    private final ModeloService modeloService;

    @GetMapping
    public ResponseEntity<List<ModeloZapato>> getAllModelos() {
        return ResponseEntity.ok(modeloService.getAllModelos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloZapato> getModeloById(@PathVariable Integer id) {
        return ResponseEntity.ok(modeloService.getModeloById(id));
    }

    @GetMapping("/marca/{idMarca}")
    public ResponseEntity<List<ModeloZapato>> getModelosByMarca(@PathVariable Integer idMarca) {
        return ResponseEntity.ok(modeloService.getModelosByMarca(idMarca));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ModeloZapato>> getModelosByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(modeloService.getModelosByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<ModeloZapato> createModelo(@RequestBody ModeloZapato modelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloService.createModelo(modelo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloZapato> updateModelo(
            @PathVariable Integer id,
            @RequestBody ModeloZapato modelo) {
        return ResponseEntity.ok(modeloService.updateModelo(id, modelo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModelo(@PathVariable Integer id) {
        modeloService.deleteModelo(id);
        return ResponseEntity.noContent().build();
    }
}

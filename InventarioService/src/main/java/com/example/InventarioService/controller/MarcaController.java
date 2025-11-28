package com.example.InventarioService.controller;

import com.example.InventarioService.model.Marca;
import com.example.InventarioService.service.MarcaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marcas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> getAllMarcas() {
        return ResponseEntity.ok(marcaService.getAllMarcas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Marca> getMarcaById(@PathVariable Integer id) {
        return ResponseEntity.ok(marcaService.getMarcaById(id));
    }

    @PostMapping
    public ResponseEntity<Marca> createMarca(@RequestBody Marca marca) {
        return ResponseEntity.status(HttpStatus.CREATED).body(marcaService.createMarca(marca));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Marca> updateMarca(@PathVariable Integer id, @RequestBody Marca marca) {
        return ResponseEntity.ok(marcaService.updateMarca(id, marca));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarca(@PathVariable Integer id) {
        marcaService.deleteMarca(id);
        return ResponseEntity.noContent().build();
    }
}

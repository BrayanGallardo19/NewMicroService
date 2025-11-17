package com.example.geografiaService.controller;

import com.example.geografiaService.entity.Comuna;
import com.example.geografiaService.service.ComunaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ComunaController {
    
    private final ComunaService comunaService;
    
    @GetMapping
    public ResponseEntity<List<Comuna>> getAllComunas() {
        return ResponseEntity.ok(comunaService.getAllComunas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Comuna> getComunaById(@PathVariable Integer id) {
        return ResponseEntity.ok(comunaService.getComunaById(id));
    }
    
    @GetMapping("/region/{idRegion}")
    public ResponseEntity<List<Comuna>> getComunasByRegion(@PathVariable Integer idRegion) {
        return ResponseEntity.ok(comunaService.getComunasByRegion(idRegion));
    }
    
    @PostMapping
    public ResponseEntity<Comuna> createComuna(@Valid @RequestBody Comuna comuna) {
        return ResponseEntity.status(HttpStatus.CREATED).body(comunaService.createComuna(comuna));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Comuna> updateComuna(@PathVariable Integer id, @Valid @RequestBody Comuna comuna) {
        return ResponseEntity.ok(comunaService.updateComuna(id, comuna));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComuna(@PathVariable Integer id) {
        comunaService.deleteComuna(id);
        return ResponseEntity.noContent().build();
    }
}

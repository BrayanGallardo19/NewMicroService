package com.example.geografiaService.controller;

import com.example.geografiaService.entity.Ciudad;
import com.example.geografiaService.service.CiudadService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ciudades")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CiudadController {
    
    private final CiudadService ciudadService;
    
    @GetMapping
    public ResponseEntity<List<Ciudad>> getAllCiudades() {
        return ResponseEntity.ok(ciudadService.getAllCiudades());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Ciudad> getCiudadById(@PathVariable Integer id) {
        return ResponseEntity.ok(ciudadService.getCiudadById(id));
    }
    
    @GetMapping("/comuna/{idComuna}")
    public ResponseEntity<List<Ciudad>> getCiudadesByComuna(@PathVariable Integer idComuna) {
        return ResponseEntity.ok(ciudadService.getCiudadesByComuna(idComuna));
    }
    
    @PostMapping
    public ResponseEntity<Ciudad> createCiudad(@Valid @RequestBody Ciudad ciudad) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ciudadService.createCiudad(ciudad));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Ciudad> updateCiudad(@PathVariable Integer id, @Valid @RequestBody Ciudad ciudad) {
        return ResponseEntity.ok(ciudadService.updateCiudad(id, ciudad));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCiudad(@PathVariable Integer id) {
        ciudadService.deleteCiudad(id);
        return ResponseEntity.noContent().build();
    }
}

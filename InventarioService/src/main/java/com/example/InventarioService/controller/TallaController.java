package com.example.InventarioService.controller;

import com.example.InventarioService.model.Talla;
import com.example.InventarioService.service.TallaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tallas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TallaController {

    private final TallaService tallaService;

    @GetMapping
    public ResponseEntity<List<Talla>> getAllTallas() {
        return ResponseEntity.ok(tallaService.getAllTallas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Talla> getTallaById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(tallaService.getTallaById(id));
    }

    @PostMapping
    public ResponseEntity<Talla> createTalla(@RequestBody Talla talla) {
        return ResponseEntity.status(HttpStatus.CREATED).body(tallaService.createTalla(talla));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Talla> updateTalla(@PathVariable("id") Integer id, @RequestBody Talla talla) {
        return ResponseEntity.ok(tallaService.updateTalla(id, talla));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTalla(@PathVariable("id") Integer id) {
        tallaService.deleteTalla(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.InventarioService.controller;

import com.example.InventarioService.model.ModeloZapato;
import com.example.InventarioService.repository.ModeloZapatoRepository;
import com.example.InventarioService.service.ModeloService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/modelos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ModeloZapatoController {

    private final ModeloService modeloService;
    private final ModeloZapatoRepository modeloRepository;

    @GetMapping
    public ResponseEntity<List<ModeloZapato>> getAllModelos() {
        return ResponseEntity.ok(modeloService.getAllModelos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloZapato> getModeloById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(modeloService.getModeloById(id));
    }

    @GetMapping("/marca/{idMarca}")
    public ResponseEntity<List<ModeloZapato>> getModelosByMarca(@PathVariable("idMarca") Integer idMarca) {
        return ResponseEntity.ok(modeloService.getModelosByMarca(idMarca));
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<ModeloZapato>> getModelosByEstado(@PathVariable("estado") String estado) {
        return ResponseEntity.ok(modeloService.getModelosByEstado(estado));
    }

    @PostMapping
    public ResponseEntity<ModeloZapato> createModelo(@RequestBody ModeloZapato modelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloService.createModelo(modelo));
    }

    @PostMapping(value = "/{id}/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ModeloZapato> uploadImagen(
            @PathVariable("id") Integer id,
            @RequestParam("imagen") MultipartFile imagen) {
        try {
            return ResponseEntity.ok(modeloService.updateImagen(id, imagen.getBytes()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/imagen")
    public ResponseEntity<?> getImagen(@PathVariable("id") Integer id) {
        return modeloRepository.findById(id)
                .map(modelo -> {
                    byte[] imagen = modelo.getImagen();
                    if (imagen == null || imagen.length == 0) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG)
                            .body(imagen);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloZapato> updateModelo(
            @PathVariable("id") Integer id,
            @RequestBody ModeloZapato modelo) {
        return ResponseEntity.ok(modeloService.updateModelo(id, modelo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModelo(@PathVariable("id") Integer id) {
        modeloService.deleteModelo(id);
        return ResponseEntity.noContent().build();
    }
}

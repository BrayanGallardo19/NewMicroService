package com.example.InventarioService.controller;

import com.example.InventarioService.repository.ModeloZapatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/modelos")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ModeloZapatoController {

    private final ModeloZapatoRepository modeloRepository;

    @GetMapping("/{id}/imagen")
    public ResponseEntity<?> getImagen(@PathVariable("id") Integer id) {
        return modeloRepository.findById(id)
                .map(modelo -> {
                    byte[] imagen = modelo.getImagen();
                    if (imagen == null || imagen.length == 0) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.ok()
                            .contentType(MediaType.IMAGE_JPEG) // Asumimos JPEG/PNG compatible
                            .body(imagen);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}

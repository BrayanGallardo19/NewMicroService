package com.example.InventarioService.controller;

import com.example.InventarioService.model.ModeloZapato;
import com.example.InventarioService.repository.ModeloZapatoRepository;
import com.example.InventarioService.service.ModeloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Modelos de Zapatos", description = "Gestión de modelos de zapatos")
public class ModeloZapatoController {

    private final ModeloService modeloService;
    private final ModeloZapatoRepository modeloRepository;

    @GetMapping
    @Operation(summary = "Listar todos los modelos", description = "Obtiene la lista completa de modelos de zapatos")
    public ResponseEntity<List<ModeloZapato>> getAllModelos() {
        return ResponseEntity.ok(modeloService.getAllModelos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener modelo por ID", description = "Obtiene un modelo específico por su identificador")
    public ResponseEntity<ModeloZapato> getModeloById(
            @Parameter(description = "ID del modelo", example = "1") @PathVariable("id") Integer id) {
        return ResponseEntity.ok(modeloService.getModeloById(id));
    }

    @GetMapping("/marca/{idMarca}")
    @Operation(summary = "Listar modelos por marca", description = "Obtiene todos los modelos de una marca específica")
    public ResponseEntity<List<ModeloZapato>> getModelosByMarca(
            @Parameter(description = "ID de la marca", example = "1") @PathVariable("idMarca") Integer idMarca) {
        return ResponseEntity.ok(modeloService.getModelosByMarca(idMarca));
    }

    @GetMapping("/estado/{estado}")
    @Operation(summary = "Listar modelos por estado", description = "Obtiene todos los modelos con un estado específico")
    public ResponseEntity<List<ModeloZapato>> getModelosByEstado(
            @Parameter(description = "Estado del modelo", example = "activo") @PathVariable("estado") String estado) {
        return ResponseEntity.ok(modeloService.getModelosByEstado(estado));
    }

    @PostMapping
    @Operation(summary = "Crear nuevo modelo", description = "Crea un nuevo modelo de zapato")
    public ResponseEntity<ModeloZapato> createModelo(@RequestBody ModeloZapato modelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(modeloService.createModelo(modelo));
    }

    @PostMapping(value = "/{id}/imagen", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Subir imagen del modelo", description = "Sube una imagen para un modelo específico")
    public ResponseEntity<ModeloZapato> uploadImagen(
            @Parameter(description = "ID del modelo", example = "1") @PathVariable("id") Integer id,
            @Parameter(description = "Archivo de imagen") @RequestParam("imagen") MultipartFile imagen) {
        try {
            return ResponseEntity.ok(modeloService.updateImagen(id, imagen.getBytes()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}/imagen")
    @Operation(summary = "Obtener imagen del modelo", description = "Descarga la imagen de un modelo específico")
    public ResponseEntity<?> getImagen(
            @Parameter(description = "ID del modelo", example = "1") @PathVariable("id") Integer id) {
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

package com.example.EntregasService.controller;

import com.example.EntregasService.model.Entrega;
import com.example.EntregasService.service.EntregaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/entregas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class EntregaController {
    
    private final EntregaService entregaService;
    
    @GetMapping
    public ResponseEntity<List<Entrega>> getAllEntregas() {
        return ResponseEntity.ok(entregaService.getAllEntregas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Entrega> getEntregaById(@PathVariable Integer id) {
        return ResponseEntity.ok(entregaService.getEntregaById(id));
    }
    
    @GetMapping("/boleta/{idBoleta}")
    public ResponseEntity<Entrega> getEntregaByBoleta(@PathVariable Integer idBoleta) {
        return ResponseEntity.ok(entregaService.getEntregaByBoleta(idBoleta));
    }
    
    @GetMapping("/transportista/{idTransportista}")
    public ResponseEntity<List<Entrega>> getEntregasByTransportista(@PathVariable Integer idTransportista) {
        return ResponseEntity.ok(entregaService.getEntregasByTransportista(idTransportista));
    }
    
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Entrega>> getEntregasByEstado(@PathVariable String estado) {
        return ResponseEntity.ok(entregaService.getEntregasByEstado(estado));
    }
    
    @PostMapping
    public ResponseEntity<Entrega> createEntrega(@Valid @RequestBody Entrega entrega) {
        return ResponseEntity.status(HttpStatus.CREATED).body(entregaService.createEntrega(entrega));
    }
    
    @PutMapping("/{id}/asignar")
    public ResponseEntity<Entrega> asignarTransportista(
            @PathVariable Integer id, 
            @RequestBody Map<String, Integer> request) {
        Integer idTransportista = request.get("idTransportista");
        return ResponseEntity.ok(entregaService.asignarTransportista(id, idTransportista));
    }
    
    @PutMapping("/{id}/estado")
    public ResponseEntity<Entrega> actualizarEstado(
            @PathVariable Integer id,
            @RequestBody Map<String, String> request) {
        String nuevoEstado = request.get("estado");
        String observacion = request.get("observacion");
        return ResponseEntity.ok(entregaService.actualizarEstado(id, nuevoEstado, observacion));
    }
}

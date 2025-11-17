package com.example.VentasService.controller;

import com.example.VentasService.entity.Boleta;
import com.example.VentasService.entity.DetalleBoleta;
import com.example.VentasService.service.VentasService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentasController {
    
    private final VentasService ventasService;
    
    @GetMapping("/boletas")
    public ResponseEntity<List<Boleta>> getAllBoletas() {
        return ResponseEntity.ok(ventasService.getAllBoletas());
    }
    
    @GetMapping("/boletas/{id}")
    public ResponseEntity<Boleta> getBoletaById(@PathVariable Integer id) {
        return ResponseEntity.ok(ventasService.getBoletaById(id));
    }
    
    @GetMapping("/boletas/cliente/{idCliente}")
    public ResponseEntity<List<Boleta>> getBoletasByCliente(@PathVariable Integer idCliente) {
        return ResponseEntity.ok(ventasService.getBoletasByCliente(idCliente));
    }
    
    @GetMapping("/boletas/vendedor/{idVendedor}")
    public ResponseEntity<List<Boleta>> getBoletasByVendedor(@PathVariable Integer idVendedor) {
        return ResponseEntity.ok(ventasService.getBoletasByVendedor(idVendedor));
    }
    
    @GetMapping("/boletas/{id}/detalles")
    public ResponseEntity<List<DetalleBoleta>> getDetallesByBoleta(@PathVariable Integer id) {
        return ResponseEntity.ok(ventasService.getDetallesByBoleta(id));
    }
    
    @PostMapping("/boletas")
    public ResponseEntity<Boleta> createBoleta(@Valid @RequestBody Boleta boleta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventasService.createBoleta(boleta));
    }
    
    @PostMapping("/detalles")
    public ResponseEntity<DetalleBoleta> addDetalleBoleta(@Valid @RequestBody DetalleBoleta detalle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventasService.addDetalleBoleta(detalle));
    }
    
    @PutMapping("/boletas/{id}/estado")
    public ResponseEntity<Boleta> actualizarEstadoBoleta(
            @PathVariable Integer id,
            @RequestBody Map<String, String> request) {
        String nuevoEstado = request.get("estado");
        return ResponseEntity.ok(ventasService.actualizarEstadoBoleta(id, nuevoEstado));
    }
    
    @DeleteMapping("/boletas/{id}")
    public ResponseEntity<Void> deleteBoleta(@PathVariable Integer id) {
        ventasService.deleteBoleta(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.VentasService.controller;

import com.example.VentasService.model.Boleta;
import com.example.VentasService.model.DetalleBoleta;
import com.example.VentasService.service.VentasService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/ventas")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class VentasController {

    private final VentasService ventasService;

    @GetMapping("/boletas")
    public ResponseEntity<List<Boleta>> getAllBoletas() {
        return ResponseEntity.ok(ventasService.getAllBoletas());
    }

    @GetMapping("/boletas/{id}")
    public ResponseEntity<Boleta> getBoletaById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ventasService.getBoletaById(id));
    }

    @GetMapping("/boletas/cliente/{idCliente}")
    public ResponseEntity<List<Boleta>> getBoletasByCliente(@PathVariable("idCliente") Integer idCliente) {
        List<Boleta> boletas = ventasService.getBoletasByCliente(idCliente);
        return ResponseEntity.ok(boletas);
    }

    @GetMapping("/boletas/vendedor/{idVendedor}")
    public ResponseEntity<List<Boleta>> getBoletasByVendedor(@PathVariable("idVendedor") Integer idVendedor) {
        return ResponseEntity.ok(ventasService.getBoletasByVendedor(idVendedor));
    }

    @GetMapping("/boletas/{id}/detalles")
    public ResponseEntity<List<DetalleBoleta>> getDetallesByBoleta(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(ventasService.getDetallesByBoleta(id));
    }

    @PostMapping("/boletas")
    public ResponseEntity<Boleta> createBoleta(@RequestBody Boleta boleta) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventasService.createBoleta(boleta));
    }

    @PostMapping("/detalles")
    public ResponseEntity<DetalleBoleta> addDetalleBoleta(@RequestBody DetalleBoleta detalle) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventasService.addDetalleBoleta(detalle));
    }

    @PutMapping("/boletas/{id}/estado")
    public ResponseEntity<Boleta> actualizarEstadoBoleta(
            @PathVariable("id") Integer id,
            @RequestBody Map<String, String> request) {
        String nuevoEstado = request.get("estado");
        return ResponseEntity.ok(ventasService.actualizarEstadoBoleta(id, nuevoEstado));
    }

    @DeleteMapping("/boletas/{id}")
    public ResponseEntity<Void> deleteBoleta(@PathVariable("id") Integer id) {
        ventasService.deleteBoleta(id);
        return ResponseEntity.noContent().build();
    }
}

package com.example.InventarioService.controller;

import com.example.InventarioService.entity.Inventario;
import com.example.InventarioService.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class InventarioController {
    
    private final InventarioService inventarioService;
    
    @GetMapping
    public ResponseEntity<List<Inventario>> getAllInventario() {
        return ResponseEntity.ok(inventarioService.getAllInventario());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Inventario> getInventarioById(@PathVariable Integer id) {
        return ResponseEntity.ok(inventarioService.getInventarioById(id));
    }
    
    @GetMapping("/modelo/{idModelo}/talla/{idTalla}")
    public ResponseEntity<Inventario> getByModeloAndTalla(
            @PathVariable Integer idModelo, 
            @PathVariable Integer idTalla) {
        return ResponseEntity.ok(inventarioService.getInventarioByModeloAndTalla(idModelo, idTalla));
    }
    
    @GetMapping("/stock-bajo")
    public ResponseEntity<List<Inventario>> getStockBajo(@RequestParam(defaultValue = "10") Integer minStock) {
        return ResponseEntity.ok(inventarioService.getStockBajo(minStock));
    }
    
    @GetMapping("/sin-stock")
    public ResponseEntity<List<Inventario>> getSinStock() {
        return ResponseEntity.ok(inventarioService.getSinStock());
    }
    
    @PostMapping
    public ResponseEntity<Inventario> createInventario(@RequestBody Inventario inventario) {
        return ResponseEntity.status(HttpStatus.CREATED).body(inventarioService.createInventario(inventario));
    }
    
    @PostMapping("/{id}/reservar")
    public ResponseEntity<Inventario> reservarStock(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> request) {
        Integer cantidad = (Integer) request.get("cantidad");
        Integer usuarioResponsable = (Integer) request.get("usuarioResponsable");
        String motivo = (String) request.get("motivo");
        return ResponseEntity.ok(inventarioService.reservarStock(id, cantidad, usuarioResponsable, motivo));
    }
    
    @PostMapping("/{id}/liberar")
    public ResponseEntity<Inventario> liberarStock(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> request) {
        Integer cantidad = (Integer) request.get("cantidad");
        Integer usuarioResponsable = (Integer) request.get("usuarioResponsable");
        String motivo = (String) request.get("motivo");
        return ResponseEntity.ok(inventarioService.liberarStock(id, cantidad, usuarioResponsable, motivo));
    }
    
    @PostMapping("/{id}/agregar")
    public ResponseEntity<Inventario> agregarStock(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> request) {
        Integer cantidad = (Integer) request.get("cantidad");
        Integer usuarioResponsable = (Integer) request.get("usuarioResponsable");
        String motivo = (String) request.get("motivo");
        return ResponseEntity.ok(inventarioService.agregarStock(id, cantidad, usuarioResponsable, motivo));
    }
}

package com.example.UsuarioService.controller;

import org.springframework.web.bind.annotation.*;
import com.example.UsuarioService.model.Cliente;
import com.example.UsuarioService.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clientes")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<List<Cliente>> obtenerTodos() {
        return ResponseEntity.ok(clienteRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Integer id) {
        return clienteRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente) {
        Cliente nuevoCliente = clienteRepository.save(cliente);
        return ResponseEntity.ok(nuevoCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cliente.setIdPersona(id);
        Cliente actualizado = clienteRepository.save(cliente);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        clienteRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

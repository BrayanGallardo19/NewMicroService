package com.example.UsuarioService.controller;

import com.example.UsuarioService.model.Contacto;
import com.example.UsuarioService.repository.ContactoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacto")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ContactoController {

    private final ContactoRepository contactoRepository;

    @PostMapping
    public ResponseEntity<Contacto> createContacto(@RequestBody Contacto contacto) {
        return ResponseEntity.ok(contactoRepository.save(contacto));
    }

    @GetMapping
    public ResponseEntity<List<Contacto>> getAllContactos() {
        return ResponseEntity.ok(contactoRepository.findAll());
    }
}

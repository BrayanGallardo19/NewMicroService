package com.example.InventarioService.service;

import com.example.InventarioService.model.Talla;
import com.example.InventarioService.repository.TallaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TallaService {

    private final TallaRepository tallaRepository;

    public List<Talla> getAllTallas() {
        return tallaRepository.findAll();
    }

    public Talla getTallaById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la talla no puede ser nulo");
        }
        return tallaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Talla no encontrada con ID: " + id));
    }

    public Talla createTalla(Talla talla) {
        if (talla == null) {
            throw new IllegalArgumentException("La talla no puede ser nula");
        }
        return tallaRepository.save(talla);
    }

    public Talla updateTalla(Integer id, Talla talla) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la talla no puede ser nulo");
        }
        if (talla == null) {
            throw new IllegalArgumentException("La talla no puede ser nula");
        }
        if (!tallaRepository.existsById(id)) {
            throw new RuntimeException("Talla no encontrada con ID: " + id);
        }
        talla.setIdTalla(id);
        return tallaRepository.save(talla);
    }

    public void deleteTalla(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la talla no puede ser nulo");
        }
        if (!tallaRepository.existsById(id)) {
            throw new RuntimeException("Talla no encontrada con ID: " + id);
        }
        tallaRepository.deleteById(id);
    }
}

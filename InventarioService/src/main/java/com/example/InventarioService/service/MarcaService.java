package com.example.InventarioService.service;

import com.example.InventarioService.model.Marca;
import com.example.InventarioService.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public List<Marca> getAllMarcas() {
        return marcaRepository.findAll();
    }

    public Marca getMarcaById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la marca no puede ser nulo");
        }
        return marcaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con ID: " + id));
    }

    public Marca createMarca(Marca marca) {
        if (marca == null) {
            throw new IllegalArgumentException("La marca no puede ser nula");
        }
        return marcaRepository.save(marca);
    }

    public Marca updateMarca(Integer id, Marca marca) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la marca no puede ser nulo");
        }
        if (marca == null) {
            throw new IllegalArgumentException("La marca no puede ser nula");
        }
        if (!marcaRepository.existsById(id)) {
            throw new RuntimeException("Marca no encontrada con ID: " + id);
        }
        marca.setIdMarca(id);
        return marcaRepository.save(marca);
    }

    public void deleteMarca(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la marca no puede ser nulo");
        }
        if (!marcaRepository.existsById(id)) {
            throw new RuntimeException("Marca no encontrada con ID: " + id);
        }
        marcaRepository.deleteById(id);
    }
}

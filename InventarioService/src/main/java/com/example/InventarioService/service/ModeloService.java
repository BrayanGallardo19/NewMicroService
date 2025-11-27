package com.example.InventarioService.service;

import com.example.InventarioService.model.ModeloZapato;
import com.example.InventarioService.repository.ModeloZapatoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModeloService {

    private final ModeloZapatoRepository modeloRepository;

    @Transactional(readOnly = true)
    public List<ModeloZapato> getAllModelos() {
        return modeloRepository.findAll();
    }

    @Transactional(readOnly = true)
    public ModeloZapato getModeloById(Integer id) {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Modelo no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public List<ModeloZapato> getModelosByMarca(Integer idMarca) {
        return modeloRepository.findByMarca_IdMarca(idMarca);
    }

    @Transactional(readOnly = true)
    public List<ModeloZapato> getModelosByEstado(String estado) {
        return modeloRepository.findByEstado(estado);
    }

    @Transactional
    public ModeloZapato createModelo(ModeloZapato modelo) {
        return modeloRepository.save(modelo);
    }

    @Transactional
    public ModeloZapato updateModelo(Integer id, ModeloZapato modeloActualizado) {
        ModeloZapato modelo = getModeloById(id);

        if (modeloActualizado.getMarca() != null) {
            modelo.setMarca(modeloActualizado.getMarca());
        }
        if (modeloActualizado.getNombreModelo() != null) {
            modelo.setNombreModelo(modeloActualizado.getNombreModelo());
        }
        if (modeloActualizado.getDescripcion() != null) {
            modelo.setDescripcion(modeloActualizado.getDescripcion());
        }
        if (modeloActualizado.getPrecioUnitario() != null) {
            modelo.setPrecioUnitario(modeloActualizado.getPrecioUnitario());
        }
        if (modeloActualizado.getImagenUrl() != null) {
            modelo.setImagenUrl(modeloActualizado.getImagenUrl());
        }
        if (modeloActualizado.getEstado() != null) {
            modelo.setEstado(modeloActualizado.getEstado());
        }

        return modeloRepository.save(modelo);
    }

    @Transactional
    public void deleteModelo(Integer id) {
        ModeloZapato modelo = getModeloById(id);
        modeloRepository.delete(modelo);
    }
}

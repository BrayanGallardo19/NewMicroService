package com.example.geografiaService.service;

import com.example.geografiaService.model.Ciudad;
import com.example.geografiaService.model.Comuna;
import com.example.geografiaService.repository.CiudadRepository;
import com.example.geografiaService.repository.ComunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CiudadService {
    
    private final CiudadRepository ciudadRepository;
    private final ComunaRepository comunaRepository;
    
    @Transactional(readOnly = true)
    public List<Ciudad> getAllCiudades() {
        return ciudadRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Ciudad getCiudadById(Integer id) {
        return ciudadRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ciudad no encontrada con id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Ciudad> getCiudadesByComuna(Integer idComuna) {
        return ciudadRepository.findByComuna_IdComuna(idComuna);
    }
    
    @Transactional
    public Ciudad createCiudad(Ciudad ciudad) {
        if (!comunaRepository.existsById(ciudad.getComuna().getIdComuna())) {
            throw new RuntimeException("La comuna especificada no existe");
        }
        return ciudadRepository.save(ciudad);
    }
    
    @Transactional
    public Ciudad updateCiudad(Integer id, Ciudad ciudad) {
        Ciudad existingCiudad = getCiudadById(id);
        existingCiudad.setNombreCiudad(ciudad.getNombreCiudad());
        if (ciudad.getComuna() != null) {
            Comuna comuna = comunaRepository.findById(ciudad.getComuna().getIdComuna())
                    .orElseThrow(() -> new RuntimeException("La comuna especificada no existe"));
            existingCiudad.setComuna(comuna);
        }
        return ciudadRepository.save(existingCiudad);
    }
    
    @Transactional
    public void deleteCiudad(Integer id) {
        if (!ciudadRepository.existsById(id)) {
            throw new RuntimeException("Ciudad no encontrada con id: " + id);
        }
        ciudadRepository.deleteById(id);
    }
}

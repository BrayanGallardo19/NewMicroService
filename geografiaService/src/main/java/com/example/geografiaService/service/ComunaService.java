package com.example.geografiaService.service;

import com.example.geografiaService.entity.Comuna;
import com.example.geografiaService.entity.Region;
import com.example.geografiaService.repository.ComunaRepository;
import com.example.geografiaService.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComunaService {
    
    private final ComunaRepository comunaRepository;
    private final RegionRepository regionRepository;
    
    @Transactional(readOnly = true)
    public List<Comuna> getAllComunas() {
        return comunaRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Comuna getComunaById(Integer id) {
        return comunaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comuna no encontrada con id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Comuna> getComunasByRegion(Integer idRegion) {
        return comunaRepository.findByRegion_IdRegion(idRegion);
    }
    
    @Transactional
    public Comuna createComuna(Comuna comuna) {
        if (!regionRepository.existsById(comuna.getRegion().getIdRegion())) {
            throw new RuntimeException("La región especificada no existe");
        }
        return comunaRepository.save(comuna);
    }
    
    @Transactional
    public Comuna updateComuna(Integer id, Comuna comuna) {
        Comuna existingComuna = getComunaById(id);
        existingComuna.setNombreComuna(comuna.getNombreComuna());
        if (comuna.getRegion() != null) {
            Region region = regionRepository.findById(comuna.getRegion().getIdRegion())
                    .orElseThrow(() -> new RuntimeException("La región especificada no existe"));
            existingComuna.setRegion(region);
        }
        return comunaRepository.save(existingComuna);
    }
    
    @Transactional
    public void deleteComuna(Integer id) {
        if (!comunaRepository.existsById(id)) {
            throw new RuntimeException("Comuna no encontrada con id: " + id);
        }
        comunaRepository.deleteById(id);
    }
}

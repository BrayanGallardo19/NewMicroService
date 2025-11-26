package com.example.geografiaService.service;

import com.example.geografiaService.model.Region;
import com.example.geografiaService.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {
    
    private final RegionRepository regionRepository;
    
    @Transactional(readOnly = true)
    public List<Region> getAllRegiones() {
        return regionRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Region getRegionById(Integer id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Región no encontrada con id: " + id));
    }
    
    @Transactional
    public Region createRegion(Region region) {
        if (regionRepository.existsByNombreRegion(region.getNombreRegion())) {
            throw new RuntimeException("Ya existe una región con ese nombre");
        }
        return regionRepository.save(region);
    }
    
    @Transactional
    public Region updateRegion(Integer id, Region region) {
        Region existingRegion = getRegionById(id);
        existingRegion.setNombreRegion(region.getNombreRegion());
        existingRegion.setCodigoRegion(region.getCodigoRegion());
        return regionRepository.save(existingRegion);
    }
    
    @Transactional
    public void deleteRegion(Integer id) {
        if (!regionRepository.existsById(id)) {
            throw new RuntimeException("Región no encontrada con id: " + id);
        }
        regionRepository.deleteById(id);
    }
}

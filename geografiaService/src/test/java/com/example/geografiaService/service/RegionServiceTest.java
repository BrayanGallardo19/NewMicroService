package com.example.geografiaService.service;

import com.example.geografiaService.model.Region;
import com.example.geografiaService.repository.RegionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegionServiceTest {

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private RegionService regionService;

    private Region region;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        region = new Region();
        region.setIdRegion(1);
        region.setNombreRegion("Metropolitana");
        region.setCodigoRegion("RM");
    }

    @Test
    void getAllRegiones() {
        when(regionRepository.findAll()).thenReturn(Arrays.asList(region));
        List<Region> regiones = regionService.getAllRegiones();
        assertNotNull(regiones);
        assertEquals(1, regiones.size());
        verify(regionRepository, times(1)).findAll();
    }

    @Test
    void getRegionById() {
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        Region found = regionService.getRegionById(1);
        assertNotNull(found);
        assertEquals(1, found.getIdRegion());
        verify(regionRepository, times(1)).findById(1);
    }

    @Test
    void createRegion() {
        when(regionRepository.save(any(Region.class))).thenReturn(region);
        Region created = regionService.createRegion(region);
        assertNotNull(created);
        assertEquals("Metropolitana", created.getNombreRegion());
        verify(regionRepository, times(1)).save(region);
    }

    @Test
    void updateRegion() {
        when(regionRepository.existsById(1)).thenReturn(true);
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(regionRepository.save(any(Region.class))).thenReturn(region);

        Region updated = regionService.updateRegion(1, region);
        assertNotNull(updated);
        assertEquals(1, updated.getIdRegion());
        verify(regionRepository, times(1)).save(region);
    }

    @Test
    void deleteRegion() {
        when(regionRepository.existsById(1)).thenReturn(true);
        doNothing().when(regionRepository).deleteById(1);

        regionService.deleteRegion(1);
        verify(regionRepository, times(1)).deleteById(1);
    }
}

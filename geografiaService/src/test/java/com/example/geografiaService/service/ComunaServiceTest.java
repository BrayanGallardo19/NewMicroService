package com.example.geografiaService.service;

import com.example.geografiaService.model.Comuna;
import com.example.geografiaService.model.Region;
import com.example.geografiaService.repository.ComunaRepository;
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

class ComunaServiceTest {

    @Mock
    private ComunaRepository comunaRepository;

    @Mock
    private RegionRepository regionRepository;

    @InjectMocks
    private ComunaService comunaService;

    private Comuna comuna;
    private Region region;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        region = new Region();
        region.setIdRegion(1);
        region.setNombreRegion("Metropolitana");

        comuna = new Comuna();
        comuna.setIdComuna(1);
        comuna.setNombreComuna("Santiago");
        comuna.setRegion(region);
    }

    @Test
    void getAllComunas() {
        when(comunaRepository.findAll()).thenReturn(Arrays.asList(comuna));
        List<Comuna> comunas = comunaService.getAllComunas();
        assertNotNull(comunas);
        assertEquals(1, comunas.size());
        verify(comunaRepository, times(1)).findAll();
    }

    @Test
    void getComunaById() {
        when(comunaRepository.findById(1)).thenReturn(Optional.of(comuna));
        Comuna found = comunaService.getComunaById(1);
        assertNotNull(found);
        assertEquals(1, found.getIdComuna());
        verify(comunaRepository, times(1)).findById(1);
    }

    @Test
    void getComunasByRegion() {
        when(comunaRepository.findByRegion_IdRegion(1)).thenReturn(Arrays.asList(comuna));
        List<Comuna> comunas = comunaService.getComunasByRegion(1);
        assertNotNull(comunas);
        assertEquals(1, comunas.size());
        verify(comunaRepository, times(1)).findByRegion_IdRegion(1);
    }

    @Test
    void createComuna() {
        when(regionRepository.existsById(1)).thenReturn(true);
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(comunaRepository.save(any(Comuna.class))).thenReturn(comuna);

        Comuna created = comunaService.createComuna(comuna);
        assertNotNull(created);
        assertEquals("Santiago", created.getNombreComuna());
        verify(comunaRepository, times(1)).save(comuna);
    }

    @Test
    void updateComuna() {
        when(comunaRepository.existsById(1)).thenReturn(true);
        when(comunaRepository.findById(1)).thenReturn(Optional.of(comuna));
        when(regionRepository.findById(1)).thenReturn(Optional.of(region));
        when(comunaRepository.save(any(Comuna.class))).thenReturn(comuna);

        Comuna updated = comunaService.updateComuna(1, comuna);
        assertNotNull(updated);
        assertEquals(1, updated.getIdComuna());
        verify(comunaRepository, times(1)).save(comuna);
    }

    @Test
    void deleteComuna() {
        when(comunaRepository.existsById(1)).thenReturn(true);
        doNothing().when(comunaRepository).deleteById(1);

        comunaService.deleteComuna(1);
        verify(comunaRepository, times(1)).deleteById(1);
    }
}

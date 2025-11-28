package com.example.geografiaService.service;

import com.example.geografiaService.model.Ciudad;
import com.example.geografiaService.model.Comuna;
import com.example.geografiaService.repository.CiudadRepository;
import com.example.geografiaService.repository.ComunaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CiudadServiceTest {

    @Mock
    private CiudadRepository ciudadRepository;

    @Mock
    private ComunaRepository comunaRepository;

    @InjectMocks
    private CiudadService ciudadService;

    @Test
    void getAllCiudades_returnsList() {
        Ciudad c1 = new Ciudad();
        c1.setIdCiudad(1);
        when(ciudadRepository.findAll()).thenReturn(List.of(c1));

        List<Ciudad> result = ciudadService.getAllCiudades();

        assertThat(result).hasSize(1);
    }

    @Test
    void getCiudadById_returnsCiudad() {
        Ciudad c1 = new Ciudad();
        c1.setIdCiudad(1);
        when(ciudadRepository.findById(1)).thenReturn(Optional.of(c1));

        Ciudad result = ciudadService.getCiudadById(1);

        assertThat(result.getIdCiudad()).isEqualTo(1);
    }

    @Test
    void createCiudad_returnsSavedCiudad() {
        Comuna comuna = new Comuna();
        comuna.setIdComuna(1);

        Ciudad c1 = new Ciudad();
        c1.setComuna(comuna);

        when(comunaRepository.existsById(1)).thenReturn(true);
        when(ciudadRepository.save(any(Ciudad.class))).thenReturn(c1);

        Ciudad result = ciudadService.createCiudad(c1);

        assertThat(result).isNotNull();
    }

    @Test
    void updateCiudad_updatesAndReturnsCiudad() {
        Comuna comuna = new Comuna();
        comuna.setIdComuna(1);

        Ciudad c1 = new Ciudad();
        c1.setNombreCiudad("Updated");
        c1.setComuna(comuna);

        Ciudad existing = new Ciudad();
        existing.setIdCiudad(1);
        existing.setNombreCiudad("Original");

        when(ciudadRepository.findById(1)).thenReturn(Optional.of(existing));
        when(comunaRepository.findById(1)).thenReturn(Optional.of(comuna));
        when(ciudadRepository.save(any(Ciudad.class))).thenReturn(c1);

        Ciudad result = ciudadService.updateCiudad(1, c1);

        assertThat(result.getNombreCiudad()).isEqualTo("Updated");
    }

    @Test
    void deleteCiudad_deletesCiudad() {
        when(ciudadRepository.existsById(1)).thenReturn(true);

        ciudadService.deleteCiudad(1);

        verify(ciudadRepository).deleteById(1);
    }
}

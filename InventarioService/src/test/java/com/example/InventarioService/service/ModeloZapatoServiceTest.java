package com.example.InventarioService.service;

import com.example.InventarioService.model.ModeloZapato;
import com.example.InventarioService.repository.ModeloZapatoRepository;
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

class ModeloZapatoServiceTest {

    @Mock
    private ModeloZapatoRepository modeloRepository;

    @InjectMocks
    private ModeloZapatoService modeloService;

    private ModeloZapato modelo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        modelo = new ModeloZapato();
        modelo.setIdModelo(1);
        modelo.setNombreModelo("Nike Air");
        modelo.setEstado("DISPONIBLE");
    }

    @Test
    void getAllModelos() {
        when(modeloRepository.findAll()).thenReturn(Arrays.asList(modelo));
        List<ModeloZapato> modelos = modeloService.getAllModelos();
        assertNotNull(modelos);
        assertEquals(1, modelos.size());
        verify(modeloRepository, times(1)).findAll();
    }

    @Test
    void getModeloById() {
        when(modeloRepository.findById(1)).thenReturn(Optional.of(modelo));
        ModeloZapato found = modeloService.getModeloById(1);
        assertNotNull(found);
        assertEquals(1, found.getIdModelo());
        verify(modeloRepository, times(1)).findById(1);
    }

    @Test
    void createModelo() {
        when(modeloRepository.save(any(ModeloZapato.class))).thenReturn(modelo);
        ModeloZapato created = modeloService.createModelo(modelo);
        assertNotNull(created);
        assertEquals("Nike Air", created.getNombreModelo());
        verify(modeloRepository, times(1)).save(modelo);
    }

    @Test
    void updateModelo() {
        when(modeloRepository.existsById(1)).thenReturn(true);
        when(modeloRepository.findById(1)).thenReturn(Optional.of(modelo));
        when(modeloRepository.save(any(ModeloZapato.class))).thenReturn(modelo);

        ModeloZapato updated = modeloService.updateModelo(1, modelo);
        assertNotNull(updated);
        assertEquals(1, updated.getIdModelo());
        verify(modeloRepository, times(1)).save(modelo);
    }

    @Test
    void deleteModelo() {
        when(modeloRepository.findById(1)).thenReturn(Optional.of(modelo));
        when(modeloRepository.save(any(ModeloZapato.class))).thenReturn(modelo);

        ModeloZapato deleted = modeloService.deleteModelo(1);
        
        assertNotNull(deleted);
        assertEquals("inactivo", deleted.getEstado());
        verify(modeloRepository, times(1)).findById(1);
        verify(modeloRepository, times(1)).save(modelo);
    }
}

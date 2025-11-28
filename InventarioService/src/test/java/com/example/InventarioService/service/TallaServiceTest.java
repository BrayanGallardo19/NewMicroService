package com.example.InventarioService.service;

import com.example.InventarioService.model.Talla;
import com.example.InventarioService.repository.TallaRepository;
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

class TallaServiceTest {

    @Mock
    private TallaRepository tallaRepository;

    @InjectMocks
    private TallaService tallaService;

    private Talla talla;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        talla = new Talla();
        talla.setIdTalla(1);
        talla.setNumeroTalla("42");
    }

    @Test
    void getAllTallas() {
        when(tallaRepository.findAll()).thenReturn(Arrays.asList(talla));
        List<Talla> tallas = tallaService.getAllTallas();
        assertNotNull(tallas);
        assertEquals(1, tallas.size());
        verify(tallaRepository, times(1)).findAll();
    }

    @Test
    void getTallaById() {
        when(tallaRepository.findById(1)).thenReturn(Optional.of(talla));
        Talla found = tallaService.getTallaById(1);
        assertNotNull(found);
        assertEquals(1, found.getIdTalla());
        verify(tallaRepository, times(1)).findById(1);
    }

    @Test
    void createTalla() {
        when(tallaRepository.save(any(Talla.class))).thenReturn(talla);
        Talla created = tallaService.createTalla(talla);
        assertNotNull(created);
        assertEquals("42", created.getNumeroTalla());
        verify(tallaRepository, times(1)).save(talla);
    }

    @Test
    void updateTalla() {
        when(tallaRepository.existsById(1)).thenReturn(true);
        when(tallaRepository.findById(1)).thenReturn(Optional.of(talla));
        when(tallaRepository.save(any(Talla.class))).thenReturn(talla);

        Talla updated = tallaService.updateTalla(1, talla);
        assertNotNull(updated);
        assertEquals(1, updated.getIdTalla());
        verify(tallaRepository, times(1)).save(talla);
    }

    @Test
    void deleteTalla() {
        when(tallaRepository.existsById(1)).thenReturn(true);
        doNothing().when(tallaRepository).deleteById(1);

        tallaService.deleteTalla(1);
        verify(tallaRepository, times(1)).deleteById(1);
    }
}

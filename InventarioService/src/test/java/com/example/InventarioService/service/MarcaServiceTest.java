package com.example.InventarioService.service;

import com.example.InventarioService.model.Marca;
import com.example.InventarioService.repository.MarcaRepository;
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
class MarcaServiceTest {

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    @Test
    void getAllMarcas_returnsList() {
        Marca m1 = new Marca();
        m1.setIdMarca(1);
        m1.setNombreMarca("Nike");

        Marca m2 = new Marca();
        m2.setIdMarca(2);
        m2.setNombreMarca("Adidas");

        when(marcaRepository.findAll()).thenReturn(List.of(m1, m2));

        List<Marca> result = marcaService.getAllMarcas();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getNombreMarca()).isEqualTo("Nike");
    }

    @Test
    void getMarcaById_returnsMarca() {
        Marca m1 = new Marca();
        m1.setIdMarca(1);
        m1.setNombreMarca("Nike");

        when(marcaRepository.findById(1)).thenReturn(Optional.of(m1));

        Marca result = marcaService.getMarcaById(1);

        assertThat(result).isNotNull();
        assertThat(result.getIdMarca()).isEqualTo(1);
    }

    @Test
    void getMarcaById_throwsException_whenNotFound() {
        when(marcaRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> marcaService.getMarcaById(1));
    }

    @Test
    void createMarca_returnsSavedMarca() {
        Marca m1 = new Marca();
        m1.setNombreMarca("Nike");

        Marca savedMarca = new Marca();
        savedMarca.setIdMarca(1);
        savedMarca.setNombreMarca("Nike");

        when(marcaRepository.save(any(Marca.class))).thenReturn(savedMarca);

        Marca result = marcaService.createMarca(m1);

        assertThat(result.getIdMarca()).isEqualTo(1);
        assertThat(result.getNombreMarca()).isEqualTo("Nike");
    }

    @Test
    void updateMarca_updatesAndReturnsMarca() {
        Marca m1 = new Marca();
        m1.setNombreMarca("Nike Updated");

        Marca existingMarca = new Marca();
        existingMarca.setIdMarca(1);
        existingMarca.setNombreMarca("Nike");

        when(marcaRepository.existsById(1)).thenReturn(true);
        when(marcaRepository.save(any(Marca.class))).thenReturn(m1);

        Marca result = marcaService.updateMarca(1, m1);

        assertThat(result.getNombreMarca()).isEqualTo("Nike Updated");
        verify(marcaRepository).save(m1);
    }

    @Test
    void deleteMarca_deletesMarca() {
        when(marcaRepository.existsById(1)).thenReturn(true);

        marcaService.deleteMarca(1);

        verify(marcaRepository).deleteById(1);
    }
}

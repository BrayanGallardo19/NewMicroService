package com.example.EntregasService.service;

import com.example.EntregasService.model.Entrega;
import com.example.EntregasService.repository.EntregaRepository;
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
class EntregaServiceTest {

    @Mock
    private EntregaRepository entregaRepository;

    @InjectMocks
    private EntregaService entregaService;

    @Test
    void getAllEntregas_returnsList() {
        Entrega e1 = new Entrega();
        e1.setIdEntrega(1);
        when(entregaRepository.findAll()).thenReturn(List.of(e1));

        List<Entrega> result = entregaService.getAllEntregas();

        assertThat(result).hasSize(1);
    }

    @Test
    void getEntregaById_returnsEntrega() {
        Entrega e1 = new Entrega();
        e1.setIdEntrega(1);
        when(entregaRepository.findById(1)).thenReturn(Optional.of(e1));

        Entrega result = entregaService.getEntregaById(1);

        assertThat(result.getIdEntrega()).isEqualTo(1);
    }

    @Test
    void createEntrega_returnsSavedEntrega() {
        Entrega e1 = new Entrega();
        when(entregaRepository.save(any(Entrega.class))).thenReturn(e1);

        Entrega result = entregaService.createEntrega(e1);

        assertThat(result).isNotNull();
    }

    @Test
    void asignarTransportista_updatesEntrega() {
        Entrega e1 = new Entrega();
        e1.setIdEntrega(1);
        e1.setEstadoEntrega("pendiente");

        when(entregaRepository.findById(1)).thenReturn(Optional.of(e1));
        when(entregaRepository.save(any(Entrega.class))).thenReturn(e1);

        Entrega result = entregaService.asignarTransportista(1, 5);

        assertThat(result.getIdTransportista()).isEqualTo(5);
        assertThat(result.getEstadoEntrega()).isEqualTo("asignada");
    }

    @Test
    void actualizarEstado_updatesEntrega() {
        Entrega e1 = new Entrega();
        e1.setIdEntrega(1);
        e1.setEstadoEntrega("asignada");

        when(entregaRepository.findById(1)).thenReturn(Optional.of(e1));
        when(entregaRepository.save(any(Entrega.class))).thenReturn(e1);

        Entrega result = entregaService.actualizarEstado(1, "entregada", "Todo ok");

        assertThat(result.getEstadoEntrega()).isEqualTo("entregada");
        assertThat(result.getObservacion()).isEqualTo("Todo ok");
    }
}

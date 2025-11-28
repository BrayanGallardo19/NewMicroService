package com.example.VentasService.service;

import com.example.VentasService.client.InventarioClient;
import com.example.VentasService.model.Boleta;
import com.example.VentasService.model.DetalleBoleta;
import com.example.VentasService.repository.BoletaRepository;
import com.example.VentasService.repository.DetalleBoletaRepository;
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
class VentasServiceTest {

    @Mock
    private BoletaRepository boletaRepository;

    @Mock
    private DetalleBoletaRepository detalleBoletaRepository;

    @Mock
    private InventarioClient inventarioClient;

    @InjectMocks
    private VentasService ventasService;

    @Test
    void getAllBoletas_returnsList() {
        Boleta b1 = new Boleta();
        b1.setIdBoleta(1);
        when(boletaRepository.findAll()).thenReturn(List.of(b1));

        List<Boleta> result = ventasService.getAllBoletas();

        assertThat(result).hasSize(1);
    }

    @Test
    void getBoletaById_returnsBoleta() {
        Boleta b1 = new Boleta();
        b1.setIdBoleta(1);
        when(boletaRepository.findById(1)).thenReturn(Optional.of(b1));

        Boleta result = ventasService.getBoletaById(1);

        assertThat(result.getIdBoleta()).isEqualTo(1);
    }

    @Test
    void createBoleta_returnsSavedBoleta() {
        Boleta b1 = new Boleta();
        when(boletaRepository.save(any(Boleta.class))).thenReturn(b1);

        Boleta result = ventasService.createBoleta(b1);

        assertThat(result).isNotNull();
    }

    @Test
    void addDetalleBoleta_savesDetalleAndUpdatesStock() {
        Boleta boleta = new Boleta();
        boleta.setIdBoleta(1);
        boleta.setMontoTotal(0);

        DetalleBoleta detalle = new DetalleBoleta();
        detalle.setIdInventario(10);
        detalle.setCantidad(2);
        detalle.setPrecioUnitario(100);
        detalle.setBoleta(boleta);

        DetalleBoleta savedDetalle = new DetalleBoleta();
        savedDetalle.setSubtotal(200);

        when(detalleBoletaRepository.save(any(DetalleBoleta.class))).thenReturn(savedDetalle);

        ventasService.addDetalleBoleta(detalle);

        verify(inventarioClient).reservarStock(10, 2);
        verify(detalleBoletaRepository).save(detalle);
        verify(boletaRepository).save(boleta); // Updates total
    }

    @Test
    void deleteBoleta_deletesBoleta() {
        when(boletaRepository.existsById(1)).thenReturn(true);

        ventasService.deleteBoleta(1);

        verify(boletaRepository).deleteById(1);
    }
}

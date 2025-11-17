package com.example.EntregasService.service;

import com.example.EntregasService.entity.Entrega;
import com.example.EntregasService.repository.EntregaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EntregaService {
    
    private final EntregaRepository entregaRepository;
    
    @Transactional(readOnly = true)
    public List<Entrega> getAllEntregas() {
        return entregaRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Entrega getEntregaById(Integer id) {
        return entregaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada con id: " + id));
    }
    
    @Transactional(readOnly = true)
    public Entrega getEntregaByBoleta(Integer idBoleta) {
        return entregaRepository.findByIdBoleta(idBoleta)
                .orElseThrow(() -> new RuntimeException("Entrega no encontrada para boleta: " + idBoleta));
    }
    
    @Transactional(readOnly = true)
    public List<Entrega> getEntregasByTransportista(Integer idTransportista) {
        return entregaRepository.findByIdTransportista(idTransportista);
    }
    
    @Transactional(readOnly = true)
    public List<Entrega> getEntregasByEstado(String estado) {
        return entregaRepository.findByEstadoEntrega(estado);
    }
    
    @Transactional
    public Entrega createEntrega(Entrega entrega) {
        return entregaRepository.save(entrega);
    }
    
    @Transactional
    public Entrega asignarTransportista(Integer idEntrega, Integer idTransportista) {
        Entrega entrega = getEntregaById(idEntrega);
        entrega.setIdTransportista(idTransportista);
        entrega.setEstadoEntrega("asignada");
        entrega.setFechaAsignacion(LocalDateTime.now());
        return entregaRepository.save(entrega);
    }
    
    @Transactional
    public Entrega actualizarEstado(Integer idEntrega, String nuevoEstado, String observacion) {
        Entrega entrega = getEntregaById(idEntrega);
        entrega.setEstadoEntrega(nuevoEstado);
        if (observacion != null) {
            entrega.setObservacion(observacion);
        }
        if ("entregada".equalsIgnoreCase(nuevoEstado)) {
            entrega.setFechaEntrega(LocalDateTime.now());
        }
        return entregaRepository.save(entrega);
    }
}

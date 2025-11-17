package com.example.VentasService.service;

import com.example.VentasService.entity.Boleta;
import com.example.VentasService.entity.DetalleBoleta;
import com.example.VentasService.repository.BoletaRepository;
import com.example.VentasService.repository.DetalleBoletaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentasService {
    
    private final BoletaRepository boletaRepository;
    private final DetalleBoletaRepository detalleBoletaRepository;
    
    @Transactional(readOnly = true)
    public List<Boleta> getAllBoletas() {
        return boletaRepository.findAll();
    }
    
    @Transactional(readOnly = true)
    public Boleta getBoletaById(Integer id) {
        return boletaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Boleta no encontrada con id: " + id));
    }
    
    @Transactional(readOnly = true)
    public List<Boleta> getBoletasByCliente(Integer idCliente) {
        return boletaRepository.findByIdCliente(idCliente);
    }
    
    @Transactional(readOnly = true)
    public List<Boleta> getBoletasByVendedor(Integer idVendedor) {
        return boletaRepository.findByIdVendedor(idVendedor);
    }
    
    @Transactional(readOnly = true)
    public List<DetalleBoleta> getDetallesByBoleta(Integer idBoleta) {
        return detalleBoletaRepository.findByBoleta_IdBoleta(idBoleta);
    }
    
    @Transactional
    public Boleta createBoleta(Boleta boleta) {
        return boletaRepository.save(boleta);
    }
    
    @Transactional
    public DetalleBoleta addDetalleBoleta(DetalleBoleta detalle) {
        return detalleBoletaRepository.save(detalle);
    }
    
    @Transactional
    public Boleta actualizarEstadoBoleta(Integer idBoleta, String nuevoEstado) {
        Boleta boleta = getBoletaById(idBoleta);
        boleta.setEstado(nuevoEstado);
        return boletaRepository.save(boleta);
    }
    
    @Transactional
    public void deleteBoleta(Integer id) {
        if (!boletaRepository.existsById(id)) {
            throw new RuntimeException("Boleta no encontrada con id: " + id);
        }
        boletaRepository.deleteById(id);
    }
}

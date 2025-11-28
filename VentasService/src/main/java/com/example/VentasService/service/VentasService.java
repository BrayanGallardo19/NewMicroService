package com.example.VentasService.service;

import com.example.VentasService.model.Boleta;
import com.example.VentasService.model.DetalleBoleta;
import com.example.VentasService.repository.BoletaRepository;
import com.example.VentasService.repository.DetalleBoletaRepository;
import com.example.VentasService.client.InventarioClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VentasService {

    private final BoletaRepository boletaRepository;
    private final DetalleBoletaRepository detalleBoletaRepository;
    private final InventarioClient inventarioClient;

    @Transactional(readOnly = true)
    public List<Boleta> getAllBoletas() {
        return boletaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Boleta getBoletaById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la boleta no puede ser nulo");
        }
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
        if (boleta.getNumeroBoleta() == null || boleta.getNumeroBoleta().isEmpty()) {
            boleta.setNumeroBoleta(UUID.randomUUID().toString());
        }
        if (boleta.getMontoTotal() == null) {
            boleta.setMontoTotal(0);
        }
        if (boleta.getFecha() == null) {
            boleta.setFecha(LocalDateTime.now());
        }
        return boletaRepository.save(boleta);
    }

    @Transactional
    public DetalleBoleta addDetalleBoleta(DetalleBoleta detalle) {
        // Calcular subtotal si no viene
        if (detalle.getSubtotal() == null) {
            Integer cantidad = detalle.getCantidad();
            Integer precio = detalle.getPrecioUnitario();
            if (cantidad != null && precio != null) {
                detalle.setSubtotal(cantidad * precio);
            } else {
                detalle.setSubtotal(0);
            }
        }

        // Reservar stock en InventarioService
        if (detalle.getIdInventario() != null && detalle.getCantidad() != null) {
            inventarioClient.reservarStock(detalle.getIdInventario(), detalle.getCantidad());
        }

        // Guardar detalle
        DetalleBoleta detalleGuardado = detalleBoletaRepository.save(detalle);

        // Actualizar total de la boleta
        Boleta boleta = detalle.getBoleta();
        if (boleta != null) {
            Integer currentTotal = boleta.getMontoTotal();
            Integer subtotal = detalleGuardado.getSubtotal();
            if (currentTotal == null)
                currentTotal = 0;
            if (subtotal == null)
                subtotal = 0;

            boleta.setMontoTotal(currentTotal + subtotal);
            boletaRepository.save(boleta);
        }

        return detalleGuardado;
    }

    @Transactional
    public Boleta actualizarEstadoBoleta(Integer idBoleta, String nuevoEstado) {
        Boleta boleta = getBoletaById(idBoleta);
        boleta.setEstado(nuevoEstado);
        return boletaRepository.save(boleta);
    }

    @Transactional
    public void deleteBoleta(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID de la boleta no puede ser nulo");
        }
        if (!boletaRepository.existsById(id)) {
            throw new RuntimeException("Boleta no encontrada con id: " + id);
        }
        boletaRepository.deleteById(id);
    }
}

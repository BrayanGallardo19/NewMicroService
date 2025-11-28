package com.example.VentasService.service;

import com.example.VentasService.dto.DetalleBoletaRequest;
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
    public DetalleBoleta addDetalleBoleta(DetalleBoletaRequest request) {
        // Validar y extraer IDs del request
        if (request.getBoleta() == null || request.getBoleta().getIdBoleta() == null) {
            throw new IllegalArgumentException("El detalle debe tener una boleta asociada");
        }
        if (request.getInventario() == null || request.getInventario().getIdInventario() == null) {
            throw new IllegalArgumentException("El detalle debe tener un inventario asociado");
        }
        
        Integer idBoleta = request.getBoleta().getIdBoleta();
        Integer idInventario = request.getInventario().getIdInventario();
        
        // Cargar la boleta completamente
        Boleta boleta = getBoletaById(idBoleta);
        
        // Crear el detalle
        DetalleBoleta detalle = new DetalleBoleta();
        detalle.setBoleta(boleta);
        detalle.setIdInventario(idInventario);
        detalle.setCantidad(request.getCantidad());
        detalle.setPrecioUnitario(request.getPrecioUnitario());
        
        // Calcular subtotal si no viene
        if (request.getSubtotal() == null) {
            Integer cantidad = request.getCantidad();
            Integer precio = request.getPrecioUnitario();
            if (cantidad != null && precio != null) {
                detalle.setSubtotal(cantidad * precio);
            } else {
                detalle.setSubtotal(0);
            }
        } else {
            detalle.setSubtotal(request.getSubtotal());
        }

        // Reservar stock en InventarioService
        if (idInventario != null && request.getCantidad() != null) {
            inventarioClient.reservarStock(idInventario, request.getCantidad());
        }

        // Guardar detalle
        DetalleBoleta detalleGuardado = detalleBoletaRepository.save(detalle);

        // Actualizar total de la boleta
        Integer currentTotal = boleta.getMontoTotal();
        Integer subtotal = detalleGuardado.getSubtotal();
        if (currentTotal == null)
            currentTotal = 0;
        if (subtotal == null)
            subtotal = 0;

        boleta.setMontoTotal(currentTotal + subtotal);
        boletaRepository.save(boleta);

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

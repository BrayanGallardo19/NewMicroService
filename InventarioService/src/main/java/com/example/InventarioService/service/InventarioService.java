package com.example.InventarioService.service;

import com.example.InventarioService.model.Inventario;
import com.example.InventarioService.model.MovimientoInventario;
import com.example.InventarioService.repository.InventarioRepository;
import com.example.InventarioService.repository.MovimientoInventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;
    private final MovimientoInventarioRepository movimientoRepository;

    @Transactional(readOnly = true)
    public List<Inventario> getAllInventario() {
        return inventarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Inventario getInventarioById(Integer id) {
        return inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado con id: " + id));
    }

    @Transactional(readOnly = true)
    public Inventario getInventarioByModeloAndTalla(Integer idModelo, Integer idTalla) {
        return inventarioRepository.findByModelo_IdModeloAndTalla_IdTalla(idModelo, idTalla)
                .orElseThrow(() -> new RuntimeException(
                        "Inventario no encontrado para modelo: " + idModelo + " y talla: " + idTalla));
    }

    @Transactional(readOnly = true)
    public List<Inventario> getStockBajo(Integer minStock) {
        return inventarioRepository.findStockBajo(minStock);
    }

    @Transactional(readOnly = true)
    public List<Inventario> getSinStock() {
        return inventarioRepository.findSinStock();
    }

    @Transactional
    public Inventario createInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    @Transactional
    public Inventario reservarStock(Integer idInventario, Integer cantidad, Integer usuarioResponsable, String motivo) {
        Inventario inventario = getInventarioById(idInventario);

        if (inventario.getStockActual() < cantidad) {
            throw new RuntimeException("Stock insuficiente. Stock actual: " + inventario.getStockActual());
        }

        Integer stockAnterior = inventario.getStockActual();
        inventario.setStockActual(stockAnterior - cantidad);
        Inventario updated = inventarioRepository.save(inventario);

        // Registrar movimiento
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setInventario(inventario);
        movimiento.setTipoMovimiento("RESERVA");
        movimiento.setCantidad(cantidad);
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockNuevo(updated.getStockActual());
        movimiento.setMotivo(motivo);
        movimiento.setUsuarioResponsable(usuarioResponsable);
        movimientoRepository.save(movimiento);

        return updated;
    }

    @Transactional
    public Inventario liberarStock(Integer idInventario, Integer cantidad, Integer usuarioResponsable, String motivo) {
        Inventario inventario = getInventarioById(idInventario);

        Integer stockAnterior = inventario.getStockActual();
        inventario.setStockActual(stockAnterior + cantidad);
        Inventario updated = inventarioRepository.save(inventario);

        // Registrar movimiento
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setInventario(inventario);
        movimiento.setTipoMovimiento("LIBERACION");
        movimiento.setCantidad(cantidad);
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockNuevo(updated.getStockActual());
        movimiento.setMotivo(motivo);
        movimiento.setUsuarioResponsable(usuarioResponsable);
        movimientoRepository.save(movimiento);

        return updated;
    }

    @Transactional
    public Inventario agregarStock(Integer idInventario, Integer cantidad, Integer usuarioResponsable, String motivo) {
        Inventario inventario = getInventarioById(idInventario);

        Integer stockAnterior = inventario.getStockActual();
        inventario.setStockActual(stockAnterior + cantidad);
        Inventario updated = inventarioRepository.save(inventario);

        // Registrar movimiento
        MovimientoInventario movimiento = new MovimientoInventario();
        movimiento.setInventario(inventario);
        movimiento.setTipoMovimiento("ENTRADA");
        movimiento.setCantidad(cantidad);
        movimiento.setStockAnterior(stockAnterior);
        movimiento.setStockNuevo(updated.getStockActual());
        movimiento.setMotivo(motivo);
        movimiento.setUsuarioResponsable(usuarioResponsable);
        movimientoRepository.save(movimiento);

        return updated;
    }
}

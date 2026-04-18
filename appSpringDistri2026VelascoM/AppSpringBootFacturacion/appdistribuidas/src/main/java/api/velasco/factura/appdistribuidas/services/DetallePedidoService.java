/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.services;

import api.velasco.factura.appdistribuidas.common.exception.ApiException;
import api.velasco.factura.appdistribuidas.models.DetallePedido;
import api.velasco.factura.appdistribuidas.repositories.DetallePedidoIRepositories;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mateo Velasco
 */

@Service
@Transactional
public class DetallePedidoService implements DetallePedidoIService {
    @Autowired
    private DetallePedidoIRepositories rpDetallePedido;

    @Override
    public List<DetallePedido> buscarTodo() {
        return this.rpDetallePedido.findAll();
    }

    @Override
    public Optional<DetallePedido> buscarPorId(Long id) {
        return this.rpDetallePedido.findById(id);
    }

    @Override
    public DetallePedido crearDetallePedido(DetallePedido detallePedido) {
        return this.rpDetallePedido.save(detallePedido);
    }

    @Override
    public DetallePedido actualizarDetallePedido(Long id, DetallePedido detallePedido) {
        DetallePedido detalleExistente = this.rpDetallePedido.findById(id)
            .orElseThrow(() -> new ApiException("Detalle de pedido no encontrado"));
        
        detalleExistente.setNombreProducto(detallePedido.getNombreProducto());
        detalleExistente.setCantidadPedido(detallePedido.getCantidadPedido());
        detalleExistente.setSubTotalDetalle(detallePedido.getSubTotalDetalle());
        
        return this.rpDetallePedido.save(detalleExistente);
    }

    @Override
    public void eliminarDetallePedido(Long id) {
        if (!this.rpDetallePedido.existsById(id)) {
            throw new ApiException("Detalle de pedido no encontrado");
        }
        this.rpDetallePedido.deleteById(id);
    }

   
}

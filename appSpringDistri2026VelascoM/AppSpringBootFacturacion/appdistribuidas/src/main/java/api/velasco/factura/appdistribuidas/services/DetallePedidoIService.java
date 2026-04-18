/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package api.velasco.factura.appdistribuidas.services;

import api.velasco.factura.appdistribuidas.models.DetallePedido;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Mateo Velasco
 */
public interface DetallePedidoIService {

    List<DetallePedido> buscarTodo();
    
    Optional<DetallePedido> buscarPorId(Long id);
    
    DetallePedido crearDetallePedido(DetallePedido cli);
    
    DetallePedido actualizarDetallePedido(Long id, DetallePedido cli);
    
    void eliminarDetallePedido(Long id);
    

}

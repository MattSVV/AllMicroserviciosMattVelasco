/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package api.velasco.factura.appdistribuidas.services;

import api.velasco.factura.appdistribuidas.common.dto.PedidoRequestDTO;
import api.velasco.factura.appdistribuidas.common.dto.PedidoResponseDTO;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author Mateo Velasco
 */
public interface PedidoIService {
    List<PedidoResponseDTO> buscarTodo();
    
    Optional<PedidoResponseDTO> buscarPorId(Long id);
    
    PedidoResponseDTO crearPedido(PedidoRequestDTO cli);
    
    PedidoResponseDTO actualizarPedido(Long id, PedidoRequestDTO cli);
    
    void eliminarPedido(Long id);
    
}

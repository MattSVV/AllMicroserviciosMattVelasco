/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package api.velasco.factura.appdistribuidas.services;

import api.velasco.factura.appdistribuidas.models.DireccionCliente;
import java.util.List;
import java.util.Optional;

public interface DireccionClienteIService {
    
    List<DireccionCliente> buscarTodo();
    
    Optional<DireccionCliente> buscarPorId(Long id);
    
    DireccionCliente crearDireccionCliente(DireccionCliente cli);
    
    DireccionCliente actualizarDireccionCliente(Long id, DireccionCliente cli);
    
    void eliminarDireccionCliente(Long id);
    
}

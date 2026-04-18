/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package api.velasco.factura.appdistribuidas.services;

import api.velasco.factura.appdistribuidas.common.dto.FacturaRequestDTO;
import api.velasco.factura.appdistribuidas.common.dto.FacturaResponseDTO;
import java.util.List;

/**
 *
 * @author Mateo Velasco
 */
public interface FacturaIService {
    
    List<FacturaResponseDTO> buscarTodo();
    
    FacturaResponseDTO buscarPorId(Long id);
    
    FacturaResponseDTO crearFactura(FacturaRequestDTO cli);
    
    FacturaResponseDTO actualizarFactura(Long id, FacturaRequestDTO cli);
    
    void eliminarFactura(Long id);
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.services;

import api.velasco.factura.appdistribuidas.common.dto.DetallePedidoRequestDTO;
import api.velasco.factura.appdistribuidas.common.dto.DetallePedidoResponseDTO;
import api.velasco.factura.appdistribuidas.common.dto.PedidoRequestDTO;
import api.velasco.factura.appdistribuidas.common.dto.PedidoResponseDTO;
import api.velasco.factura.appdistribuidas.models.DetallePedido;
import api.velasco.factura.appdistribuidas.models.DireccionCliente;
import api.velasco.factura.appdistribuidas.models.Pedido;
import api.velasco.factura.appdistribuidas.repositories.PedidoIRepositories;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PedidoServices implements PedidoIService {
    
@Autowired
    private PedidoIRepositories rpPedido;

    @Autowired
    private DireccionClienteServices direccionClienteService;

    @Override
    public List<PedidoResponseDTO> buscarTodo() {
        return rpPedido.findAll().stream()
                .map(this::convertirAPedidoResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PedidoResponseDTO> buscarPorId(Long id) {
        return rpPedido.findById(id)
                .map(this::convertirAPedidoResponseDTO);
    }

    @Override
    public PedidoResponseDTO crearPedido(PedidoRequestDTO request) {
        // 1. Obtener la dirección del cliente
        DireccionCliente direccion = direccionClienteService.buscarPorId(request.getDireccionClienteId())
                .orElseThrow(() -> new RuntimeException("Dirección cliente no encontrada"));

        // 2. Crear entidad Pedido
        Pedido pedido = new Pedido();
        pedido.setFechaPedido(request.getFechaPedido());
        pedido.setDireccionCliente(direccion);
        pedido.setTotalPedido(request.getTotalPedido() != null ? 
                              request.getTotalPedido() : BigDecimal.ZERO);

        // 3. Crear los detalles
        List<DetallePedido> detalles = new ArrayList<>();
        for (DetallePedidoRequestDTO detDTO : request.getDetalles()) {
            DetallePedido detalle = new DetallePedido();
            detalle.setNombreProducto(detDTO.getNombreProducto());
            detalle.setCantidadPedido(detDTO.getCantidad());
            detalle.setSubTotalDetalle(detDTO.getSubTotal());
            detalle.setPedido(pedido); // Relación bidireccional
            detalles.add(detalle);
        }

        pedido.setDetalles(detalles);

        // 4. Calcular total si es necesario
        if (pedido.getTotalPedido().compareTo(BigDecimal.ZERO) == 0) {
            pedido.setTotalPedido(calcularTotal(detalles));
        }

        // 5. Guardar y retornar
        Pedido pedidoGuardado = rpPedido.save(pedido);
        return convertirAPedidoResponseDTO(pedidoGuardado);
    }

    @Override
    public void eliminarPedido(Long id) {
        if (!rpPedido.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado");
        }
        rpPedido.deleteById(id);
    }

    // ==================== MÉTODOS PRIVADOS (ESTILO CLÁSICO) ====================

    private BigDecimal calcularTotal(List<DetallePedido> detalles) {
        BigDecimal total = BigDecimal.ZERO;
        for (DetallePedido d : detalles) {
            if (d.getSubTotalDetalle() != null) {
                total = total.add(d.getSubTotalDetalle());
            }
        }
        return total;
    }

    private PedidoResponseDTO convertirAPedidoResponseDTO(Pedido pedido) {
        // Mapeo manual de la lista de detalles
        List<DetallePedidoResponseDTO> detallesDTO = new ArrayList<>();
        
        for (DetallePedido det : pedido.getDetalles()) {
            DetallePedidoResponseDTO dDto = new DetallePedidoResponseDTO();
            dDto.setId(det.getIdDetallePedido());
            dDto.setNombreProducto(det.getNombreProducto());
            dDto.setCantidad(det.getCantidadPedido());
            dDto.setSubTotal(det.getSubTotalDetalle());
            detallesDTO.add(dDto);
        }

        // Mapeo manual del DTO de respuesta principal
        PedidoResponseDTO response = new PedidoResponseDTO();
        response.setIdPedido(pedido.getIdPedido());
        response.setFechaPedido(pedido.getFechaPedido());
        response.setTotalPedido(pedido.getTotalPedido());
        
        if (pedido.getDireccionCliente() != null) {
            response.setDireccionClienteId(pedido.getDireccionCliente().getIdDireccionCliente());
        }
        
        response.setDetalles(detallesDTO);

        return response;
    }    

    @Override

public PedidoResponseDTO actualizarPedido(Long id, PedidoRequestDTO request) {
    // 1. Verificar si el pedido existe
    Pedido pedidoExistente = rpPedido.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido no encontrado con ID: " + id));

    // 2. Actualizar datos básicos del Pedido
    pedidoExistente.setFechaPedido(request.getFechaPedido());

    // 4. Actualizar Detalles (Enfoque: Reemplazar lista actual)
    // Limpiamos la lista actual para evitar duplicados o inconsistencias
    pedidoExistente.getDetalles().clear();

    List<DetallePedido> nuevosDetalles = new ArrayList<>();
    for (DetallePedidoRequestDTO detDTO : request.getDetalles()) {
        DetallePedido detalle = new DetallePedido();
        detalle.setNombreProducto(detDTO.getNombreProducto());
        detalle.setCantidadPedido(detDTO.getCantidad());
        detalle.setSubTotalDetalle(detDTO.getSubTotal());
        detalle.setPedido(pedidoExistente); // Mantenemos la relación bidireccional
        nuevosDetalles.add(detalle);
    }
    
    pedidoExistente.getDetalles().addAll(nuevosDetalles);

    // 5. Recalcular el total
    if (request.getTotalPedido() != null && request.getTotalPedido().compareTo(BigDecimal.ZERO) > 0) {
        pedidoExistente.setTotalPedido(request.getTotalPedido());
    } else {
        pedidoExistente.setTotalPedido(calcularTotal(pedidoExistente.getDetalles()));
    }

    // 6. Guardar cambios y convertir a DTO de respuesta
    Pedido pedidoActualizado = rpPedido.save(pedidoExistente);
    return convertirAPedidoResponseDTO(pedidoActualizado);
}
}

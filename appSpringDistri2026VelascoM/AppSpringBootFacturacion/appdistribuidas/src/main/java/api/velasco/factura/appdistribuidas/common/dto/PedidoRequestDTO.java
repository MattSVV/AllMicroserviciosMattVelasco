/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.common.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class PedidoRequestDTO {
    
    @NotNull(message = "La fecha del pedido es obligatoria")
    private LocalDate fechaPedido;

    private BigDecimal totalPedido;   // opcional, lo calcularemos si viene null

    @NotNull(message = "La dirección del cliente es obligatoria")
    private Long direccionClienteId;

    @NotNull(message = "El pedido debe tener al menos un detalle")
    private List<DetallePedidoRequestDTO> detalles;

    public PedidoRequestDTO() {
    }

    public PedidoRequestDTO(LocalDate fechaPedido, BigDecimal totalPedido, Long direccionClienteId, List<DetallePedidoRequestDTO> detalles) {
        this.fechaPedido = fechaPedido;
        this.totalPedido = totalPedido;
        this.direccionClienteId = direccionClienteId;
        this.detalles = detalles;
    }

    
    
    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDate fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public BigDecimal getTotalPedido() {
        return totalPedido;
    }

    public void setTotalPedido(BigDecimal totalPedido) {
        this.totalPedido = totalPedido;
    }

    public Long getDireccionClienteId() {
        return direccionClienteId;
    }

    public void setDireccionClienteId(Long direccionClienteId) {
        this.direccionClienteId = direccionClienteId;
    }

    public List<DetallePedidoRequestDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoRequestDTO> detalles) {
        this.detalles = detalles;
    }
    
    
    
    
}

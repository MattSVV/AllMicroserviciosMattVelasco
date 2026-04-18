/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.common.dto.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Mateo Velasco
 */
public class PedidoRabbitMQEvent {
    @JsonProperty("pedido_id")
    private Long pedidoId;
    
    @JsonProperty("fecha_pedido")
    private String fechaPedido;
    
    private BigDecimal total;
    
    private List<DetalleEvento> detalles;
    
    @JsonProperty("direccion_cliente")
    private DireccionEvento direccion;

    // Getters y Setters clásicos
    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
    public List<DetalleEvento> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleEvento> detalles) { this.detalles = detalles; }
    public DireccionEvento getDireccion() { return direccion; }
    public void setDireccion(DireccionEvento direccion) { this.direccion = direccion; }
}





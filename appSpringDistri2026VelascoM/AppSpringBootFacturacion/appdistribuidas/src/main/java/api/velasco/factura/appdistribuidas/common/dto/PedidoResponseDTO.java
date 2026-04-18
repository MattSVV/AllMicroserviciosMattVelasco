/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author Mateo Velasco
 */
public class PedidoResponseDTO {
    
    private Long idPedido;
    private LocalDate fechaPedido;
    private BigDecimal totalPedido;
    private Long direccionClienteId;
    private String direccionClienteNombre;   // opcional: para mostrar info útil
    private List<DetallePedidoResponseDTO> detalles;

    public PedidoResponseDTO() {
    }

    
    
    public PedidoResponseDTO(Long idPedido, LocalDate fechaPedido, BigDecimal totalPedido, Long direccionClienteId, String direccionClienteNombre, List<DetallePedidoResponseDTO> detalles) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.totalPedido = totalPedido;
        this.direccionClienteId = direccionClienteId;
        this.direccionClienteNombre = direccionClienteNombre;
        this.detalles = detalles;
    }

    
    
    public Long getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(Long idPedido) {
        this.idPedido = idPedido;
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

    public String getDireccionClienteNombre() {
        return direccionClienteNombre;
    }

    public void setDireccionClienteNombre(String direccionClienteNombre) {
        this.direccionClienteNombre = direccionClienteNombre;
    }

    public List<DetallePedidoResponseDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoResponseDTO> detalles) {
        this.detalles = detalles;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.common.dto;

import api.velasco.factura.appdistribuidas.models.Factura;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 *
 * @author Mateo Velasco
 */
public class FacturaResponseDTO {
    
    private Long idFactura;
    private Long numeroFactura;
    private LocalDate fechaEmision;
    private BigDecimal subtotalPedido; // El total que viene del Pedido
    private BigDecimal porcentajeIva;   // El 15%
    private BigDecimal valorIva;        // El cálculo en dinero del IVA
    private BigDecimal totalFactura;    // subtotal + valorIva
    private String metodoPago;
    private String estadoFactura;
    private Long pedidoId;

    // Constructor vacío
    public FacturaResponseDTO() {}

    // Getters y Setters
    public Long getIdFactura() { return idFactura; }
    public void setIdFactura(Long idFactura) { this.idFactura = idFactura; }

    public Long getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(Long numeroFactura) { this.numeroFactura = numeroFactura; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public BigDecimal getSubtotalPedido() { return subtotalPedido; }
    public void setSubtotalPedido(BigDecimal subtotalPedido) { this.subtotalPedido = subtotalPedido; }

    public BigDecimal getPorcentajeIva() { return porcentajeIva; }
    public void setPorcentajeIva(BigDecimal porcentajeIva) { this.porcentajeIva = porcentajeIva; }

    public BigDecimal getValorIva() { return valorIva; }
    public void setValorIva(BigDecimal valorIva) { this.valorIva = valorIva; }

    public BigDecimal getTotalFactura() { return totalFactura; }
    public void setTotalFactura(BigDecimal totalFactura) { this.totalFactura = totalFactura; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstadoFactura() { return estadoFactura; }
    public void setEstadoFactura(String estadoFactura) { this.estadoFactura = estadoFactura; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
    
    public FacturaResponseDTO convertirAFacturaResponseDTO(Factura factura) {
    FacturaResponseDTO dto = new FacturaResponseDTO();
    
    dto.setIdFactura(factura.getIdFactura());
    dto.setNumeroFactura(factura.getNumeroFactura());
    dto.setFechaEmision(factura.getFechaEmision());
    dto.setMetodoPago(factura.getMetodoPago());
    dto.setEstadoFactura(factura.getEstadoFactura());
    
    if (factura.getPedido() != null) {
        dto.setPedidoId(factura.getPedido().getIdPedido());
        
        // Obtener el subtotal del pedido
        BigDecimal subtotal = factura.getPedido().getTotalPedido();
        dto.setSubtotalPedido(subtotal);
        
        // El porcentaje de IVA (asumiendo que en tu entidad 'iva' guardas el 15)
        BigDecimal porcentaje = factura.getIva(); 
        dto.setPorcentajeIva(porcentaje);
        
        // Cálculo del valor del IVA: (Subtotal * 15) / 100
        BigDecimal valorIva = subtotal.multiply(porcentaje)
                                      .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        dto.setValorIva(valorIva);
        
        // Cálculo del Total: Subtotal + ValorIva
        dto.setTotalFactura(subtotal.add(valorIva));
    }

    return dto;
}
    
}

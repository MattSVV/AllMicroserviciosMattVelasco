package api.velasco.factura.appdistribuidas.common.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FacturaRequestDTO {
    private Long numeroFactura;
    private LocalDate fechaEmision;
    private BigDecimal iva; // Aquí recibimos el porcentaje, ej: 15
    private String metodoPago;
    private String estadoFactura;
    private Long pedidoId;

    public FacturaRequestDTO() {}

    // Getters y Setters
    public Long getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(Long numeroFactura) { this.numeroFactura = numeroFactura; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public BigDecimal getIva() { return iva; }
    public void setIva(BigDecimal iva) { this.iva = iva; }

    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }

    public String getEstadoFactura() { return estadoFactura; }
    public void setEstadoFactura(String estadoFactura) { this.estadoFactura = estadoFactura; }

    public Long getPedidoId() { return pedidoId; }
    public void setPedidoId(Long pedidoId) { this.pedidoId = pedidoId; }
}
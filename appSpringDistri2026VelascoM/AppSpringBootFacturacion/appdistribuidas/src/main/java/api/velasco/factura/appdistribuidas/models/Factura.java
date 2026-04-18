/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Facturas")
public class Factura implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFactura;

    @Column(name = "numero_factura", nullable = false, unique = true)
    private Long numeroFactura;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "total_factura", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFactura;

    @Column(name = "iva_factura", nullable = false, precision = 10, scale = 2)
    private BigDecimal iva;

    @Column(name = "metodo_pago_factira", nullable = false)
    private String metodoPago; 

    @Column(name = "estado_factura", nullable = false)
    private String estadoFactura;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;
    

    public Factura() {
    }
    
    

    public Factura(Long idFactura, Long numeroFactura, LocalDate fechaEmision, BigDecimal totalFactura, BigDecimal iva, String metodoPago, String estadoFactura, Pedido pedido) {
        this.idFactura = idFactura;
        this.numeroFactura = numeroFactura;
        this.fechaEmision = fechaEmision;
        this.totalFactura = totalFactura;
        this.iva = iva;
        this.metodoPago = metodoPago;
        this.estadoFactura = estadoFactura;
        this.pedido = pedido;
    }
    
    

    public Long getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(Long idFactura) {
        this.idFactura = idFactura;
    }

    public Long getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getEstadoFactura() {
        return estadoFactura;
    }

    public void setEstadoFactura(String estadoFactura) {
        this.estadoFactura = estadoFactura;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    
    
}

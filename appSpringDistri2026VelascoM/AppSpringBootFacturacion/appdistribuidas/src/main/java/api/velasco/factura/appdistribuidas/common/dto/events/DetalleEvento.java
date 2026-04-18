/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.common.dto.events;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

/**
 *
 * @author Mateo Velasco
 */
public class DetalleEvento {
     @JsonProperty("nombre_producto")
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal subtotal;

    public DetalleEvento(String nombreProducto, Integer cantidad, BigDecimal subtotal) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public DetalleEvento() {
    }
    
    // Getters y Setters clásicos
    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}

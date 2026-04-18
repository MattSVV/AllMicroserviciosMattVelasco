/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.common.dto;

import java.math.BigDecimal;


/**
 *
 * @author Mateo Velasco
 */
public class DetallePedidoResponseDTO {
    
    private Long id;
    private String nombreProducto;
    private Integer cantidad;
    private BigDecimal subTotal;

    public DetallePedidoResponseDTO(Long id, String nombreProducto, Integer cantidad, BigDecimal subTotal) {
        this.id = id;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    public DetallePedidoResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    
    
    
    
}

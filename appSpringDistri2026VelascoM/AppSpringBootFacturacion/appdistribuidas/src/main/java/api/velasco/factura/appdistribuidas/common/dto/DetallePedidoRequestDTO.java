/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 *
 * @author Mateo Velasco
 */
public class DetallePedidoRequestDTO {
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombreProducto;

    @NotNull
    @Positive(message = "La cantidad debe ser mayor a 0")
    private Integer cantidad;

    @NotNull
    @Positive(message = "El subtotal debe ser mayor a 0")
    private BigDecimal subTotal;

    public DetallePedidoRequestDTO(String nombreProducto, Integer cantidad, BigDecimal subTotal) {
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.subTotal = subTotal;
    }

    public DetallePedidoRequestDTO() {
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

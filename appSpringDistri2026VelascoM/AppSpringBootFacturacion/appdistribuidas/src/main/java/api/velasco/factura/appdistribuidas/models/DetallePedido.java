/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_pedidos")
public class DetallePedido implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_detalle_pedido")
    private Long idDetallePedido;
    
    @Column(name = "nombre_producto", nullable = false)
    private String nombreProducto;

    @Column(name = "cantidad_detalle_pedido", nullable = false)
    private int cantidadPedido;

    @Column(name = "sub_total_detalle_pedido", precision = 10, scale = 2)
    private BigDecimal subTotalDetalle;
    
    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Pedido pedido;

    public DetallePedido() {
    }

    public DetallePedido(Long idDetallePedido, String nombreProducto, int cantidadPedido, BigDecimal subTotalDetalle, Pedido pedido) {
        this.idDetallePedido = idDetallePedido;
        this.nombreProducto = nombreProducto;
        this.cantidadPedido = cantidadPedido;
        this.subTotalDetalle = subTotalDetalle;
        this.pedido = pedido;
    }

    public Long getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(Long idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidadPedido() {
        return cantidadPedido;
    }

    public void setCantidadPedido(int cantidadPedido) {
        this.cantidadPedido = cantidadPedido;
    }

    public BigDecimal getSubTotalDetalle() {
        return subTotalDetalle;
    }

    public void setSubTotalDetalle(BigDecimal subTotalDetalle) {
        this.subTotalDetalle = subTotalDetalle;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
    
    
}

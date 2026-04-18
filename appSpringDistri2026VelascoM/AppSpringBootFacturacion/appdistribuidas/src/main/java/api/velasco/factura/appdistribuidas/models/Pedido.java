/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "fecha_pedido", nullable = false)
    private LocalDate fechaPedido;

    @Column(name = "total_pedido", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPedido;

    // Relación real con la entidad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "direccion_cliente_id", nullable = false)
    private DireccionCliente direccionCliente;

    // Campo auxiliar para recibir SOLO el ID desde JSON
    @Transient
    private Long direccionClienteId;
    
    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    public Pedido() {
        
    }
    
    public Pedido(Long idPedido, LocalDate fechaPedido, BigDecimal totalPedido, DireccionCliente direccionCliente, List<DetallePedido> detalles, Long direccionClienteId) {
        this.idPedido = idPedido;
        this.fechaPedido = fechaPedido;
        this.totalPedido = totalPedido;
        this.direccionCliente = direccionCliente;
        this.detalles = detalles;
        this.direccionClienteId = direccionClienteId;
    }

    public Long getDireccionClienteId() {
        return direccionClienteId;
    }

    public void setDireccionClienteId(Long direccionClienteId) {
        this.direccionClienteId = direccionClienteId;
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

    public DireccionCliente getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(DireccionCliente direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
    
    
}

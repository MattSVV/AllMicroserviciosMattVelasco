/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "DireccionClientes")
public class DireccionCliente implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idDireccionCliente;
    
    @Column(name = "nombreCliente")
    private String nombresCompletosCliente;
    
    @Column(name = "direccionCliente")
    private String direccionCliente;
    
    
    @OneToMany(mappedBy = "direccionCliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pedido> pedidos;
    

    public DireccionCliente(long idDireccionCliente, String nombresCompletosCliente, String direccionCliente, List<Pedido> pedidos) {
        this.idDireccionCliente = idDireccionCliente;
        this.nombresCompletosCliente = nombresCompletosCliente;
        this.direccionCliente = direccionCliente;
        this.pedidos = pedidos;
    }
    
    public DireccionCliente() {
        
    }


    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    public long getIdDireccionCliente() {
        return idDireccionCliente;
    }

    public void setIdDireccionCliente(long idDireccionCliente) {
        this.idDireccionCliente = idDireccionCliente;
    }

    public String getNombresCompletosCliente() {
        return nombresCompletosCliente;
    }

    public void setNombresCompletosCliente(String nombresCompletosCliente) {
        this.nombresCompletosCliente = nombresCompletosCliente;
    }

    public String getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(String direccionCliente) {
        this.direccionCliente = direccionCliente;
    }
    
    
}

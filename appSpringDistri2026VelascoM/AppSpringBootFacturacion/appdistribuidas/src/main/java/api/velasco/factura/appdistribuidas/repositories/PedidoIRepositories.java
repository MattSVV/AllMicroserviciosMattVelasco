/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package api.velasco.factura.appdistribuidas.repositories;

import api.velasco.factura.appdistribuidas.models.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Mateo Velasco
 */
public interface PedidoIRepositories extends JpaRepository<Pedido, Long>{
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package api.velasco.factura.appdistribuidas.repositories;

import api.velasco.factura.appdistribuidas.models.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Mateo Velasco
 */
public interface FacturaIRepositories extends JpaRepository<Factura, Long> {
    
}

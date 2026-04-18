/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.services;

import api.velasco.factura.appdistribuidas.common.exception.ApiException;
import api.velasco.factura.appdistribuidas.models.DireccionCliente;
import api.velasco.factura.appdistribuidas.repositories.DireccionClienteIRepositories;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class DireccionClienteServices implements DireccionClienteIService{

    @Autowired
    private DireccionClienteIRepositories rpCli;

    @Override
    public List<DireccionCliente> buscarTodo() {
        return this.rpCli.findAll();
    }

    @Override
    public Optional<DireccionCliente> buscarPorId(Long id) {
        return this.rpCli.findById(id);
    }

    @Override
    public DireccionCliente crearDireccionCliente(DireccionCliente cli) {
        return this.rpCli.save(cli);
    }

    @Override
    public DireccionCliente actualizarDireccionCliente(Long id, DireccionCliente cli) {
        DireccionCliente cliente = this.rpCli.findById(id)
            .orElseThrow(() -> new ApiException("Dirección no encontrada"));
        
        cliente.setNombresCompletosCliente(cli.getNombresCompletosCliente());
        
        return this.rpCli.save(cliente);
    }

    @Override
    public void eliminarDireccionCliente(Long id) {
        if (!this.rpCli.existsById(id)) {
            throw new ApiException("Dirección no encontrada");
        }
        this.rpCli.deleteById(id);
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.controllers;

import api.velasco.factura.appdistribuidas.models.DireccionCliente;
import api.velasco.factura.appdistribuidas.services.DireccionClienteServices;

import api.velasco.factura.appdistribuidas.common.dto.ApiResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/direcciones")
@CrossOrigin(origins = "*")
public class DireccionClienteController {
    
    @Autowired
    private DireccionClienteServices direcService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DireccionCliente>>> obtenerTodas() {
        List<DireccionCliente> direcciones = direcService.buscarTodo();
        return ResponseEntity.ok(ApiResponse.ok(direcciones, "Direcciones obtenidas correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DireccionCliente>> obtenerPorId(@PathVariable Long id) {
        return direcService.buscarPorId(id)
            .map(direc -> ResponseEntity.ok(ApiResponse.ok(direc, "Dirección encontrada")))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Dirección no encontrada")));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<DireccionCliente>> crear(@RequestBody DireccionCliente direccion) {
        DireccionCliente creada = direcService.crearDireccionCliente(direccion);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.ok(creada, "Dirección creada correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DireccionCliente>> actualizar(
            @PathVariable Long id,
            @RequestBody DireccionCliente direccion) {
        DireccionCliente actualizada = direcService.actualizarDireccionCliente(id, direccion);
        return ResponseEntity.ok(ApiResponse.ok(actualizada, "Dirección actualizada correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        direcService.eliminarDireccionCliente(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Dirección eliminada correctamente"));
    }
}

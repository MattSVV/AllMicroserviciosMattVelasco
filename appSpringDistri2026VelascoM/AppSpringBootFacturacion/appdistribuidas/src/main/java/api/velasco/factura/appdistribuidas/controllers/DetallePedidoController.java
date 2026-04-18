/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.controllers;


import api.velasco.factura.appdistribuidas.common.dto.ApiResponse;
import api.velasco.factura.appdistribuidas.models.DetallePedido;
import api.velasco.factura.appdistribuidas.services.DetallePedidoService;
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
@RequestMapping("/api/v1/DetallePedido")
@CrossOrigin(origins = "*")
public class DetallePedidoController {
    
    @Autowired
    private DetallePedidoService detalleService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<DetallePedido>>> obtenerTodos() {
        List<DetallePedido> detalles = detalleService.buscarTodo();
        return ResponseEntity.ok(ApiResponse.ok(detalles, "Detalles de pedido obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DetallePedido>> obtenerPorId(@PathVariable Long id) {
        return detalleService.buscarPorId(id)
            .map(detalle -> ResponseEntity.ok(ApiResponse.ok(detalle, "Detalle encontrado")))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Detalle no encontrado")));
    }


    @PostMapping
    public ResponseEntity<ApiResponse<DetallePedido>> crear(@RequestBody DetallePedido detallePedido) {
        DetallePedido creado = detalleService.crearDetallePedido(detallePedido);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.ok(creado, "Detalle de pedido creado correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DetallePedido>> actualizar(
            @PathVariable Long id,
            @RequestBody DetallePedido detallePedido) {
        DetallePedido actualizado = detalleService.actualizarDetallePedido(id, detallePedido);
        return ResponseEntity.ok(ApiResponse.ok(actualizado, "Detalle de pedido actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        detalleService.eliminarDetallePedido(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Detalle de pedido eliminado correctamente"));
    }
    
}

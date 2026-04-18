/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.controllers;


import api.velasco.factura.appdistribuidas.common.dto.ApiResponse;
import api.velasco.factura.appdistribuidas.common.dto.PedidoRequestDTO;
import api.velasco.factura.appdistribuidas.common.dto.PedidoResponseDTO;
import api.velasco.factura.appdistribuidas.services.PedidoServices;
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
@RequestMapping("/api/v1/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController {
@Autowired
    private PedidoServices pedidoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PedidoResponseDTO>>> obtenerTodos() {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarTodo();
        return ResponseEntity.ok(ApiResponse.ok(pedidos, "Pedidos obtenidos correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponseDTO>> obtenerPorId(@PathVariable Long id) {
        return pedidoService.buscarPorId(id)
                .map(pedido -> ResponseEntity.ok(ApiResponse.ok(pedido, "Pedido encontrado")))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.error("Pedido no encontrado")));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PedidoResponseDTO>> crear(@RequestBody PedidoRequestDTO pedidoRequest) {
        PedidoResponseDTO creado = pedidoService.crearPedido(pedidoRequest);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.ok(creado, "Pedido creado correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PedidoResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody PedidoRequestDTO pedidoRequest) {
        
        PedidoResponseDTO actualizado = pedidoService.actualizarPedido(id, pedidoRequest);
        return ResponseEntity.ok(ApiResponse.ok(actualizado, "Pedido actualizado correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        pedidoService.eliminarPedido(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Pedido eliminado correctamente"));
    }
}

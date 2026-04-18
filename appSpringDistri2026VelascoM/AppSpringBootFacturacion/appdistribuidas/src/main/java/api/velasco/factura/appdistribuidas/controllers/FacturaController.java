package api.velasco.factura.appdistribuidas.controllers;

import api.velasco.factura.appdistribuidas.common.dto.ApiResponse;
import api.velasco.factura.appdistribuidas.common.dto.FacturaRequestDTO;
import api.velasco.factura.appdistribuidas.common.dto.FacturaResponseDTO;
import api.velasco.factura.appdistribuidas.services.FacturaServices;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/factura")
@CrossOrigin(origins = "*")
public class FacturaController {

    @Autowired
    private FacturaServices facturaService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<FacturaResponseDTO>>> obtenerTodas() {
        List<FacturaResponseDTO> facturas = facturaService.buscarTodo();
        return ResponseEntity.ok(ApiResponse.ok(facturas, "Facturas obtenidas correctamente"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<FacturaResponseDTO>> obtenerPorId(@PathVariable Long id) {
        // Como el Service ahora devuelve FacturaResponseDTO directamente o lanza excepción
        FacturaResponseDTO factura = facturaService.buscarPorId(id);
        return ResponseEntity.ok(ApiResponse.ok(factura, "Factura encontrada"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FacturaResponseDTO>> crear(@RequestBody FacturaRequestDTO facturaDto) {
        FacturaResponseDTO creada = facturaService.crearFactura(facturaDto);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(ApiResponse.ok(creada, "Factura creada correctamente"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<FacturaResponseDTO>> actualizar(
            @PathVariable Long id,
            @RequestBody FacturaRequestDTO facturaDto) {
        FacturaResponseDTO actualizada = facturaService.actualizarFactura(id, facturaDto);
        return ResponseEntity.ok(ApiResponse.ok(actualizada, "Factura actualizada correctamente"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> eliminar(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.ok(ApiResponse.ok(null, "Factura eliminada correctamente"));
    }    
}
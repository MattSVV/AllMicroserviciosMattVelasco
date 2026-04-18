package api.velasco.factura.appdistribuidas.services;

import api.velasco.factura.appdistribuidas.common.dto.FacturaRequestDTO;
import api.velasco.factura.appdistribuidas.common.dto.FacturaResponseDTO;
import api.velasco.factura.appdistribuidas.common.exception.ApiException;
import api.velasco.factura.appdistribuidas.models.Factura;
import api.velasco.factura.appdistribuidas.models.Pedido;
import api.velasco.factura.appdistribuidas.repositories.FacturaIRepositories;
import api.velasco.factura.appdistribuidas.repositories.PedidoIRepositories; // Asumiendo este nombre
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class FacturaServices implements FacturaIService {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FacturaServices.class);

    @Autowired
    private FacturaIRepositories rpFactura;
    
    @Autowired
    private PedidoIRepositories rpPedido; // Necesario para obtener el subtotal del pedido

    @Override
    public List<FacturaResponseDTO> buscarTodo() {
        return this.rpFactura.findAll().stream()
                .map(this::convertirAFacturaResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public FacturaResponseDTO buscarPorId(Long id) {
        Factura factura = this.rpFactura.findById(id)
                .orElseThrow(() -> new ApiException("Factura no encontrada"));
        return convertirAFacturaResponseDTO(factura);
    }

    @Override
    public FacturaResponseDTO crearFactura(FacturaRequestDTO request) {

    log.info("===== INICIO CREAR FACTURA =====");

    // 🔹 LOG del request completo
    log.info("Request recibido: {}", request);

    log.info("Pedido ID recibido: {}", request.getPedidoId());
    log.info("Número factura: {}", request.getNumeroFactura());
    log.info("IVA recibido: {}", request.getIva());
    log.info("Método pago: {}", request.getMetodoPago());
    log.info("Estado factura: {}", request.getEstadoFactura());

    // 1. Buscar el pedido
    Pedido pedido = rpPedido.findById(request.getPedidoId())
            .orElseThrow(() -> new ApiException("Pedido no encontrado"));

    // 🔹 LOG del pedido obtenido
    log.info("Pedido encontrado: {}", pedido);
    log.info("Total del pedido: {}", pedido.getTotalPedido());
    log.info("Fecha pedido: {}", pedido.getFechaPedido());

    // 2. Crear factura
    Factura factura = new Factura();
    factura.setNumeroFactura(request.getNumeroFactura());
    factura.setFechaEmision(request.getFechaEmision());
    factura.setIva(request.getIva());
    factura.setMetodoPago(request.getMetodoPago());
    factura.setEstadoFactura(request.getEstadoFactura());
    factura.setPedido(pedido);

    // 3. Validar subtotal
    BigDecimal subtotal = pedido.getTotalPedido();

    log.info("Subtotal antes de validar: {}", subtotal);

    if (subtotal == null) {
        log.error("❌ ERROR: El subtotal del pedido es NULL");
        throw new ApiException("El pedido #" + request.getPedidoId() + " no tiene total registrado.");
    }

    // 🔹 LOG antes del cálculo
    log.info("Calculando total con IVA...");
    log.info("Subtotal: {}", subtotal);
    log.info("IVA: {}", request.getIva());

    BigDecimal totalCalculado = calcularTotalConIva(subtotal, request.getIva());

    log.info("Total calculado: {}", totalCalculado);

    factura.setTotalFactura(totalCalculado);

    Factura facturaGuardada = this.rpFactura.save(factura);

    log.info("Factura guardada con ID: {}", facturaGuardada.getIdFactura());
    log.info("===== FIN CREAR FACTURA =====");

    return convertirAFacturaResponseDTO(facturaGuardada);
}

    @Override
    public FacturaResponseDTO actualizarFactura(Long id, FacturaRequestDTO request) {
        Factura facturaExistente = this.rpFactura.findById(id)
                .orElseThrow(() -> new ApiException("Factura no encontrada"));

        facturaExistente.setNumeroFactura(request.getNumeroFactura());
        facturaExistente.setFechaEmision(request.getFechaEmision());
        facturaExistente.setIva(request.getIva());
        facturaExistente.setMetodoPago(request.getMetodoPago());
        facturaExistente.setEstadoFactura(request.getEstadoFactura());

        // Recalcular el total si el IVA o el pedido cambiaron
        BigDecimal totalCalculado = calcularTotalConIva(facturaExistente.getPedido().getTotalPedido(), request.getIva());
        facturaExistente.setTotalFactura(totalCalculado);
        
        return convertirAFacturaResponseDTO(this.rpFactura.save(facturaExistente));
    }

    @Override
    public void eliminarFactura(Long id) {
        if (!this.rpFactura.existsById(id)) {
            throw new ApiException("Factura no encontrada");
        }
        this.rpFactura.deleteById(id);
    }

    // ==================== MÉTODOS PRIVADOS DE CÁLCULO Y CONVERSIÓN ====================

    private BigDecimal calcularTotalConIva(BigDecimal subtotal, BigDecimal porcentajeIva) {
        if (subtotal == null) return BigDecimal.ZERO;
        // IVA = (Subtotal * 15) / 100
        BigDecimal valorIva = subtotal.multiply(porcentajeIva)
                                     .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
        return subtotal.add(valorIva);
    }

    private FacturaResponseDTO convertirAFacturaResponseDTO(Factura factura) {
        FacturaResponseDTO dto = new FacturaResponseDTO();
        dto.setIdFactura(factura.getIdFactura());
        dto.setNumeroFactura(factura.getNumeroFactura());
        dto.setFechaEmision(factura.getFechaEmision());
        dto.setMetodoPago(factura.getMetodoPago());
        dto.setEstadoFactura(factura.getEstadoFactura());
        
        if (factura.getPedido() != null) {
            dto.setPedidoId(factura.getPedido().getIdPedido());
            BigDecimal subtotal = factura.getPedido().getTotalPedido();
            dto.setSubtotalPedido(subtotal);
            
            BigDecimal porcentaje = factura.getIva(); 
            dto.setPorcentajeIva(porcentaje);
            
            // Valor monetario del IVA
            BigDecimal valorIva = subtotal.multiply(porcentaje)
                                          .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
            dto.setValorIva(valorIva);
            
            // Total ya calculado en la entidad o recalculado aquí
            dto.setTotalFactura(factura.getTotalFactura());
        }
        return dto;
    }

}

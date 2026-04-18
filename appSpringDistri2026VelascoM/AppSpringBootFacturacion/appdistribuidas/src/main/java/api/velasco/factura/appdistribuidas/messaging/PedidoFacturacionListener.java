/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package api.velasco.factura.appdistribuidas.messaging;

import api.velasco.factura.appdistribuidas.common.dto.DetallePedidoRequestDTO;
import api.velasco.factura.appdistribuidas.common.dto.PedidoRequestDTO;
import api.velasco.factura.appdistribuidas.common.dto.events.DetalleEvento;
import api.velasco.factura.appdistribuidas.common.dto.events.PedidoRabbitMQEvent;
import api.velasco.factura.appdistribuidas.services.PedidoServices;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mateo Velasco
 */

@Component
public class PedidoFacturacionListener {
    @Autowired
    private PedidoServices pedidoService;

    // Escucha la cola que viene de Python
    @RabbitListener(queues = "pedido_facturacion")
    public void recibirPedido(PedidoRabbitMQEvent evento) {
        System.out.println(">>> Mensaje recibido de RabbitMQ: Pedido #" + evento.getPedidoId());

        try {
            // 1. Convertir el evento de Rabbit al DTO de creación de Pedido
            PedidoRequestDTO request = new PedidoRequestDTO();
            request.setFechaPedido(LocalDate.now()); 
            request.setTotalPedido(evento.getTotal());
            
            // Aquí asignamos un ID de dirección. 
            // Si necesitas guardar una dirección nueva, primero deberías llamar a tu DireccionService
            request.setDireccionClienteId(1L); 

            // 2. Mapear los detalles del evento a los DTOs de tu servicio
            List<DetallePedidoRequestDTO> detallesDto = new ArrayList<>();
            for (DetalleEvento detEv : evento.getDetalles()) {
                DetallePedidoRequestDTO d = new DetallePedidoRequestDTO();
                d.setNombreProducto(detEv.getNombreProducto());
                d.setCantidad(detEv.getCantidad());
                d.setSubTotal(detEv.getSubtotal());
                detallesDto.add(d);
            }
            request.setDetalles(detallesDto);

            // 3. Guardar en la Base de Datos usando tu servicio existente
            pedidoService.crearPedido(request);
            
            System.out.println(">>> Pedido y detalles guardados correctamente en la BD.");
            
        } catch (Exception e) {
            System.err.println("XXX Error al procesar el pedido de RabbitMQ: " + e.getMessage());
        }
    }
}
    
    


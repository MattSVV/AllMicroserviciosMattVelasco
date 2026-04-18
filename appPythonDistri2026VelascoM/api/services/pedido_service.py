from decimal import Decimal
from datetime import datetime
from sqlalchemy.orm import Session
from fastapi import HTTPException

from api.core.database import Database
from api.models.models import Pedido, PedidoDetalle, Producto, DireccionCliente
from api.schemas.schemas import PedidoCreate
from api.services.publis_pedidos import publish_pedido_to_rabbitmq   # Asegúrate que la ruta sea correcta


class PedidoService:
    def __init__(self, database: Database):
        self.db = database

    def create_pedido(self, pedido_in: PedidoCreate):
        session: Session = self.db.get_session()
        try:
            # Validar dirección del cliente
            direccion_cliente = session.get(DireccionCliente, pedido_in.direccion_cliente_id)
            if not direccion_cliente:
                raise HTTPException(status_code=404, detail="Dirección de cliente no encontrada")

            # Crear el pedido
            pedido = Pedido(
                direccion_cliente_id=pedido_in.direccion_cliente_id,
                fecha_pedido=datetime.utcnow(),
                total=Decimal('0.00')
            )
            session.add(pedido)
            session.flush()   # Para obtener el ID

            total_pedido = Decimal('0.00')
            detalles_rabbitmq = []

            # Procesar los detalles
            for det_in in pedido_in.detalles:
                producto = session.get(Producto, det_in.producto_id)
                if not producto:
                    raise HTTPException(
                        status_code=404, 
                        detail=f"Producto ID {det_in.producto_id} no encontrado"
                    )

                if producto.stock < det_in.cantidad:
                    raise HTTPException(
                        status_code=400,
                        detail=f"Stock insuficiente para {producto.nombre}"
                    )

                subtotal = producto.precio_unitario * det_in.cantidad

                # Crear detalle
                detalle = PedidoDetalle(
                    pedido_id=pedido.id,
                    producto_id=det_in.producto_id,
                    cantidad=det_in.cantidad,
                    precio_unitario=producto.precio_unitario,   # snapshot
                    subtotal=subtotal
                )
                session.add(detalle)

                total_pedido += subtotal

                # Datos para RabbitMQ
                detalles_rabbitmq.append({
                    "nombre_producto": producto.nombre,
                    "cantidad": det_in.cantidad,
                    "subtotal": float(subtotal)
                })

            # Actualizar total
            pedido.total = total_pedido

            # Guardar todo
            session.commit()
            session.refresh(pedido)

            # Publicar a RabbitMQ
            direccion_data = {
                "nombre_completo": direccion_cliente.nombre_completo,
                "direccion": direccion_cliente.direccion
            }

            publish_pedido_to_rabbitmq(
                pedido={
                    "id": pedido.id,
                    "total": float(pedido.total),
                    "fecha_pedido": pedido.fecha_pedido
                },
                direccion=direccion_data,
                detalles=detalles_rabbitmq
            )

            print(f"✅ Pedido creado y enviado a facturación | ID: {pedido.id}")
            return pedido

        except HTTPException:
            session.rollback()
            raise
        except Exception as e:
            session.rollback()
            raise HTTPException(status_code=500, detail=f"Error al crear pedido: {str(e)}")
        finally:
            session.close()
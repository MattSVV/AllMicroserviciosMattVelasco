import pika
from datetime import datetime
from decimal import Decimal

from api.core.config import RABBITMQ
from api.schemas.schemas import PedidoRabbitMQ, DireccionClienteEvent, PedidoDetalleEvent


def publish_pedido_to_rabbitmq(pedido: dict, direccion: dict, detalles: list):
    """
    Publica el pedido en formato PedidoRabbitMQ a la cola de facturación.
    """
    try:
        # Construir los objetos según tus schemas
        direccion_event = DireccionClienteEvent(
            nombre_completo=direccion.get("nombre_completo", ""),
            direccion=direccion.get("direccion", "")
        )

        detalles_event = [
            PedidoDetalleEvent(
                nombre_producto=det["nombre_producto"],
                cantidad=det["cantidad"],
                subtotal=Decimal(str(det["subtotal"]))
            )
            for det in detalles
        ]

        pedido_event = PedidoRabbitMQ(
            pedido_id=pedido["id"],
            fecha_pedido=pedido.get("fecha_pedido", datetime.utcnow()),
            direccion_cliente=direccion_event,
            total=Decimal(str(pedido["total"])),
            detalles=detalles_event
        )

        # Convertir a JSON
        message_json = pedido_event.model_dump_json()

        # Conexión y publicación
        credentials = pika.PlainCredentials(
            RABBITMQ["username"], RABBITMQ["password"]
        )
        connection = pika.BlockingConnection(
            pika.ConnectionParameters(
                host=RABBITMQ["hostname"],
                port=RABBITMQ["port"],
                virtual_host=RABBITMQ["virtualHost"],
                credentials=credentials,
                heartbeat=600
            )
        )
        channel = connection.channel()

        queue_name = RABBITMQ.get("queue_facturacion", "pedido_facturacion")
        channel.queue_declare(queue=queue_name, durable=True)

        channel.basic_publish(
            exchange='',
            routing_key=queue_name,
            body=message_json,
            properties=pika.BasicProperties(
                delivery_mode=2,
                content_type='application/json'
            )
        )

        print(f"📤 Pedido publicado correctamente en RabbitMQ | ID: {pedido['id']}")
        connection.close()

    except Exception as e:
        print(f"❌ Error al publicar en RabbitMQ: {str(e)}")
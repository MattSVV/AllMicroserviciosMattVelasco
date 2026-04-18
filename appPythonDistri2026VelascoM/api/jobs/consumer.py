import pika
import json
import sys
import signal
from datetime import datetime

from api.core.config import RABBITMQ
from api.services.generic_services import GenericService
from api.models.models import DireccionCliente
from api.core.database import Database

db = Database()
service = GenericService(DireccionCliente, db)


def callback(ch, method, properties, body):
    try:
        data = json.loads(body)

        #  Validación de campos
        obj = {
            "cliente_id": data.get("ClienteId"),
            "nombre_completo": data.get("NombreCompleto"),
            "email": data.get("Email"),
            "direccion": data.get("DireccionCompleta")
        }

        # Validar que no falten campos importantes
        if  not obj["cliente_id"] or not obj["nombre_completo"]:
            raise ValueError("Faltan campos obligatorios en el mensaje")

        # Insertar en base de datos
        nuevo_registro = service.create(obj)

        print(f"✅ Insertado correctamente en BD | ID: {nuevo_registro.id} | Cliente: {obj['nombre_completo']}")

        # Confirmar que procesamos el mensaje correctamente
        ch.basic_ack(delivery_tag=method.delivery_tag)

    except json.JSONDecodeError:
        print("❌ Error: El mensaje no es un JSON válido")
        ch.basic_nack(delivery_tag=method.delivery_tag, requeue=False)
    except KeyError as e:
        print(f"❌ Error: Clave faltante en el mensaje: {e}")
        ch.basic_nack(delivery_tag=method.delivery_tag, requeue=False)
    except Exception as e:
        print(f"❌ Error al insertar en BD: {str(e)}")
        ch.basic_nack(delivery_tag=method.delivery_tag, requeue=False)


def start():
    print("🚀 Iniciando Consumer RabbitMQ (DireccionCliente)...")

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
    print("✅ Conectado a RabbitMQ")

    channel.queue_declare(queue=RABBITMQ["queue"], durable=True)

    channel.basic_qos(prefetch_count=1)

    channel.basic_consume(
        queue=RABBITMQ["queue"],
        on_message_callback=callback,
        auto_ack=False        # ← Muy importante
    )

    print(f"📡 Escuchando cola: {RABBITMQ['queue']}")
    print("   (Presiona Ctrl+C para detener)")

    def signal_handler(sig, frame):
        print("\n🛑 Deteniendo consumer...")
        connection.close()
        sys.exit(0)

    signal.signal(signal.SIGINT, signal_handler)
    signal.signal(signal.SIGTERM, signal_handler)

    try:
        channel.start_consuming()
    except KeyboardInterrupt:
        connection.close()


if __name__ == "__main__":
    start()
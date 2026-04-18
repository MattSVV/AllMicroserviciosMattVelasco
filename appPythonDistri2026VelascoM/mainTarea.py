"""
abrir terminal para crear entorno vitual:
  py -m venv myenv    
Ingresar al entorno virtual:
  .\myenv\Scripts\activate

Instalar la libreria pika:
pip install pika


main.py

Suscriptor de RabbitMQ que escucha la cola: clienteDireccionEvent
e imprime los mensajes recibidos en consola.
"""

import pika
import json
from dotenv import load_dotenv
import os

load_dotenv()

# ==============================
# CONFIGURACIÓN DE RABBITMQ
# ==============================
RABBITMQ_CONFIG = {
    "username": os.getenv("RABBITMQ_USER"),
    "password": os.getenv("RABBITMQ_PASSWORD"),
    "virtualHost": os.getenv("RABBITMQ_VHOST"),
    "port": os.getenv("RABBITMQ_PORT"),
    "hostname": os.getenv("RABBITMQ_HOSTNAME")
}

QUEUE_NAME = os.getenv("RABBITMQ_QUEUE")


# ==============================
# FUNCIÓN CALLBACK
# ==============================
def callback(ch, method, properties, body):
    """
    Esta función se ejecuta cada vez que llega un mensaje a la cola.
    """
    try:
        # Convertir el mensaje (bytes) a string y luego a JSON
        mensaje = json.loads(body.decode("utf-8"))

        print("\n📩 Mensaje recibido:")
        print(f"Id: {mensaje.get('ClienteId')}")
        print(f"Nombre Completos: {mensaje.get('NombreCompleto')}")
        print(f"Email: {mensaje.get('Email')}")
        print(f"Direccion Completa: {mensaje.get('DireccionCompleta')}")
        print("-" * 40)


        #guardar en una tabla DireccionCliente
        #  //BDD EcPedidos mysql tabla
        # public class DireccionCliente
        # {
        #     public int id { get; set; } 
        #     public int ClienteId { get; set; }
        #     public string NombreCompleto { get; set; } = string.Empty;
        #     public string Email { get; set; } = string.Empty;
        #     public string DireccionCompleta { get; set; } = string.Empty;
        # }

        

    except Exception as e:
        print("❌ Error procesando mensaje:", str(e))


# ==============================
# CONEXIÓN A RABBITMQ
# ==============================
def conectar_rabbitmq():
    """
    Crea la conexión con RabbitMQ usando las credenciales configuradas.
    """
    credentials = pika.PlainCredentials(
        RABBITMQ_CONFIG["username"],
        RABBITMQ_CONFIG["password"]
    )

    parameters = pika.ConnectionParameters(
        host=RABBITMQ_CONFIG["hostname"],
        port=RABBITMQ_CONFIG["port"],
        virtual_host=RABBITMQ_CONFIG["virtualHost"],
        credentials=credentials
    )

    return pika.BlockingConnection(parameters)


# ==============================
# FUNCIÓN PRINCIPAL
# ==============================
def main():
    print("🚀 Iniciando suscriptor RabbitMQ...")

    try:
        # Crear conexión
        connection = conectar_rabbitmq()
        channel = connection.channel()

        # Declarar la cola (por si no existe)
        channel.queue_declare(queue=QUEUE_NAME, durable=True)

        print(f"📡 Escuchando la cola: {QUEUE_NAME}")
        print("⏳ Esperando mensajes... Presiona CTRL + C para salir\n")

        # Configurar consumo
        channel.basic_consume(
            queue=QUEUE_NAME,
            on_message_callback=callback,
            auto_ack=True  # Confirmación automática
        )

        # Iniciar consumo
        channel.start_consuming()

    except KeyboardInterrupt:
        print("\n🛑 Suscriptor detenido por el usuario")

    except Exception as e:
        print("❌ Error en la conexión:", str(e))


# ==============================
# ENTRY POINT
# ==============================
if __name__ == "__main__":
    main()
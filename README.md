🚀 Sistema Distribuido de Gestión Comercial
📌 Descripción

Este proyecto consiste en una aplicación basada en arquitectura de microservicios, donde cada servicio está desarrollado en un lenguaje diferente y cumple una responsabilidad específica dentro del flujo de negocio.

El sistema permite gestionar:

👤 Clientes
🛒 Pedidos
🧾 Facturación
🌐 Consumo centralizado mediante un frontend

Todo esto mediante comunicación asíncrona y APIs integradas.

🧩 Arquitectura

El sistema está dividido en 3 microservicios principales + 1 frontend:

🔹 Microservicio de Clientes
Lenguaje: C# (.NET)
Base de datos: SQL Server
Responsabilidad:
Registro de clientes
Gestión de direcciones
Estructura enviada:
Cliente + Dirección en un solo objeto
🔹 Microservicio de Pedidos
Lenguaje: Python
Base de datos: MySQL
Responsabilidad:
Gestión de pedidos
Detalle de productos
Estructura enviada:
Pedido con detalle de productos concatenados
🔹 Microservicio de Facturación
Lenguaje: Java
Base de datos: PostgreSQL
Responsabilidad:
Generación de facturas
Consumo de datos de clientes y pedidos
🌐 Frontend (Cliente Web)
Lenguaje: TypeScript
Responsabilidad:
Consumo de APIs de los microservicios
Interfaz de usuario unificada
Gestión del flujo completo (cliente → pedido → factura)
🔄 Comunicación entre servicios

Se utilizó mensajería asíncrona con:

📦 RabbitMQ
Flujo de comunicación:
C# (Cliente + Dirección)
        ↓
   RabbitMQ
        ↓
Python (Pedido + Detalles)
        ↓
   RabbitMQ
        ↓
Java (Generación de Factura)
Flujo desde el frontend:
Frontend (TypeScript)
   ↓        ↓        ↓
 C# API   Python API   Java API
🛠️ Tecnologías utilizadas
C# / .NET
Python
Java
TypeScript
RabbitMQ
SQL Server
MySQL
PostgreSQL
🧠 Decisiones de diseño
✔️ Separación de responsabilidades por microservicio
✔️ Uso de múltiples lenguajes (arquitectura heterogénea)
✔️ Comunicación desacoplada mediante mensajería
✔️ Integración mediante APIs para el frontend
✔️ Envío de objetos completos para reducir dependencias
⚙️ Estructura de datos
📌 Cliente (C#)

Incluye:

Datos personales
Dirección asociada
📌 Pedido (Python)

Incluye:

Información del pedido
Lista de productos (concatenados)
📌 Factura (Java)

Incluye:

Datos del cliente
Detalles del pedido
Total calculado
▶️ Cómo ejecutar el proyecto
Clonar el repositorio:
git clone <URL_DEL_REPOSITORIO>
Levantar RabbitMQ
Ejecutar los microservicios:
Servicio de clientes (C#)
Servicio de pedidos (Python)
Servicio de facturación (Java)
Ejecutar el frontend:
npm install
npm run dev
📈 Posibles mejoras
🔐 Autenticación y autorización (JWT)
🌐 API Gateway
🐳 Dockerización del sistema
📊 Monitoreo (Prometheus / Grafana)
🔁 Manejo de errores y reintentos en RabbitMQ
⚡ Optimización de estructura de datos (evitar concatenaciones)
👨‍💻 Autor

Mateo Velasco
Proyecto académico - Arquitectura de Software Distribuida

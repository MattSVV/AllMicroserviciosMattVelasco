from pydantic import BaseModel
from datetime import datetime
from typing import List
from decimal import Decimal

# ==========================================
# PRODUCTOS (Gestión / Pestaña de productos)
# ==========================================

class ProductoCreate(BaseModel):
    nombre: str
    descripcion: str
    precio_unitario: Decimal
    stock: int


class ProductoResponse(BaseModel):
    id: int
    nombre: str
    descripcion: str
    precio_unitario: Decimal
    stock: int

    class Config:
        from_attributes = True


# ==========================================
# PEDIDO - CREACION DESDE CAJERO (POS)
# ==========================================

class PedidoDetalleCreate(BaseModel):
    producto_id: int
    cantidad: int
    

class PedidoCreate(BaseModel):
    direccion_cliente_id: int
    detalles: List[PedidoDetalleCreate]


class PedidoResponse(BaseModel):
    id: int
    direccion_cliente_id: int
    fecha_pedido: datetime
    total: Decimal

    class Config:
        from_attributes = True


# ==========================================
# EVENTO RABBITMQ (FACTURACION)
# ==========================================

class DireccionClienteEvent(BaseModel):
    nombre_completo: str
    direccion: str


class PedidoDetalleEvent(BaseModel):
    nombre_producto: str
    cantidad: int
    subtotal: Decimal


class PedidoRabbitMQ(BaseModel):
    pedido_id: int
    fecha_pedido: datetime
    direccion_cliente: DireccionClienteEvent
    total: Decimal
    detalles: List[PedidoDetalleEvent]
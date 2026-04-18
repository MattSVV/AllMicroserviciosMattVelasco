from sqlalchemy import Column, Integer, String, DateTime, Numeric, ForeignKey
from sqlalchemy.orm import relationship
from api.core.database import Base
from datetime import datetime


class DireccionCliente(Base):
    __tablename__ = "direcciones_clientes"

    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    cliente_id = Column(Integer)
    nombre_completo = Column(String(255))
    email = Column(String(255))
    direccion = Column(String(500))


class Producto(Base):
    __tablename__ = "productos"

    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    nombre = Column(String(255), nullable=False)
    descripcion = Column(String(500))
    precio_unitario = Column(Numeric(10, 2), nullable=False)
    stock = Column(Integer, nullable=False)

    detalles = relationship("PedidoDetalle", back_populates="producto")

class Pedido(Base):
    __tablename__ = "pedidos"

    id = Column(Integer, primary_key=True, index=True, autoincrement=True)
    direccion_cliente_id = Column(Integer, ForeignKey("direcciones_clientes.id"))
    fecha_pedido = Column(DateTime)
    total = Column(Numeric(10, 2))

    detalles = relationship("PedidoDetalle", back_populates="pedido", cascade="all, delete-orphan")
    direccion_cliente = relationship("DireccionCliente")   # ← AGREGAR ESTA LÍNEA


class PedidoDetalle(Base):
    __tablename__ = "pedido_detalles"

    id = Column(Integer, primary_key=True, index=True, autoincrement=True)

    pedido_id = Column(Integer, ForeignKey("pedidos.id"), nullable=False)
    producto_id = Column(Integer, ForeignKey("productos.id"), nullable=False)

    cantidad = Column(Integer, nullable=False)
    precio_unitario = Column(Numeric(10, 2), nullable=False)  # snapshot del precio
    subtotal = Column(Numeric(10, 2), nullable=False)

    pedido = relationship("Pedido", back_populates="detalles")
    producto = relationship("Producto", back_populates="detalles")


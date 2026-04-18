from fastapi import APIRouter, HTTPException
from api.services.generic_services import GenericService
from api.models.models import PedidoDetalle
from api.core.database import Database
from fastapi.middleware.cors import CORSMiddleware
from pydantic import BaseModel
from decimal import Decimal



router_detalle_pedido = APIRouter()

db = Database()
detalle_service = GenericService(PedidoDetalle, db)



# Schema simple para CRUD directo
class PedidoDetalleCRUD(BaseModel):
    pedido_id: int
    producto_id: int
    cantidad: int
    precio_unitario: Decimal
    subtotal: Decimal

    class Config:
        from_attributes = True


@router_detalle_pedido.get("/pedido-detalles", tags=["pedido_detalles"], response_model=list[PedidoDetalleCRUD])
def get_all():
    return detalle_service.get_all()


@router_detalle_pedido.get("/pedido-detalles/{id}", tags=["pedido_detalles"], response_model=PedidoDetalleCRUD)
def get(id: int):
    data = detalle_service.get_by_id(id)
    if not data:
        raise HTTPException(404, "Detalle no encontrado")
    return data


@router_detalle_pedido.post("/pedido-detalles", tags=["pedido_detalles"], response_model=PedidoDetalleCRUD)
def create(detalle: PedidoDetalleCRUD):
    return detalle_service.create(detalle.dict())


@router_detalle_pedido.put("/pedido-detalles/{id}", tags=["pedido_detalles"], response_model=PedidoDetalleCRUD)
def update(id: int, detalle: PedidoDetalleCRUD):
    data = detalle_service.update(id, detalle.dict())
    if not data:
        raise HTTPException(404, "Detalle no encontrado")
    return data


@router_detalle_pedido.delete("/pedido-detalles/{id}", tags=["pedido_detalles"])
def delete(id: int):
    if not detalle_service.delete(id):
        raise HTTPException(404, "Detalle no encontrado")
    return {"message": "Detalle eliminado"}
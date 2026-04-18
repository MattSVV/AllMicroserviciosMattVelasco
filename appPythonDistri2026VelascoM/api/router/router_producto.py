from fastapi import APIRouter, HTTPException
from api.services.generic_services import GenericService
from api.models.models import Producto
from api.schemas.schemas import ProductoCreate, ProductoResponse
from api.core.database import Database

router_producto = APIRouter()

db = Database()
producto_service = GenericService(Producto, db)


@router_producto.get("/productos", tags=["productos"], response_model=list[ProductoResponse])
def get_all():
    return producto_service.get_all()


@router_producto.get("/productos/{id}", tags=["productos"], response_model=ProductoResponse)
def get(id: int):
    data = producto_service.get_by_id(id)
    if not data:
        raise HTTPException(status_code=404, detail="Producto no encontrado")
    return data


@router_producto.post("/productos", tags=["productos"], response_model=ProductoResponse)
def create(producto: ProductoCreate):
    return producto_service.create(producto.dict())


@router_producto.put("/productos/{id}", tags=["productos"], response_model=ProductoResponse)
def update(id: int, producto: ProductoCreate):
    data = producto_service.update(id, producto.dict())
    if not data:
        raise HTTPException(status_code=404, detail="Producto no encontrado")
    return data


@router_producto.delete("/productos/{id}", tags=["productos"])
def delete(id: int):
    if not producto_service.delete(id):
        raise HTTPException(status_code=404, detail="Producto no encontrado")
    return {"message": "Producto eliminado correctamente"}
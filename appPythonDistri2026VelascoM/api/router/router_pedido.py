from fastapi import APIRouter, HTTPException, logger
from api.services.pedido_service import PedidoService
from api.schemas.schemas import PedidoCreate, PedidoResponse
from api.core.database import Database
from api.services.generic_services import GenericService
from api.models.models import Pedido

router_pedido = APIRouter()

db = Database()
pedido_service = PedidoService(db)


@router_pedido.post("/pedidos", tags=['pedidos'], response_model=PedidoResponse)
def create(pedido: PedidoCreate):
    try:
        # Intentamos ejecutar la lógica del servicio
        return pedido_service.create_pedido(pedido)
    except HTTPException as he:
        # Si el error es una excepción de FastAPI conocida, la relanzamos
        raise he
    except Exception as e:
        # Si es un error inesperado (DB, RabbitMQ, etc.), lo capturamos
        logger.error(f"Error crítico en el endpoint de pedidos: {str(e)}")
        # Lanzamos un 500 con el detalle del error para que React lo vea
        raise HTTPException(
            status_code=500, 
            detail=f"Error interno del servidor: {str(e)}"
        )

# Endpoints restantes con GenericService
generic = GenericService(Pedido, db)

@router_pedido.get("/pedidos", tags=['pedidos'], response_model=list[PedidoResponse])
def get_all():
    return generic.get_all()

@router_pedido.get("/pedidos/{id}", tags=['pedidos'], response_model=PedidoResponse)
def get(id: int):
    data = generic.get_by_id(id)
    if not data:
        raise HTTPException(status_code=404, detail="Pedido no encontrado")
    return data

@router_pedido.put("/pedidos/{id}", tags=['pedidos'], response_model=PedidoResponse)
def update(id: int, pedido: PedidoCreate):
    data = generic.update(id, pedido.dict())
    if not data:
        raise HTTPException(status_code=404, detail="Pedido no encontrado")
    return data

@router_pedido.delete("/pedidos/{id}", tags=['pedidos'])
def delete(id: int):
    if not generic.delete(id):
        raise HTTPException(status_code=404, detail="Pedido no encontrado")
    return {"message": "Pedido eliminado"}
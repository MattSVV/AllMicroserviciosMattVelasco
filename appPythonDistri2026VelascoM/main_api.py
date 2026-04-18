from fastapi.responses import HTMLResponse
from fastapi import FastAPI
from fastapi.middleware.cors import CORSMiddleware
from api.router.router_pedido import router_pedido
from api.router.router_producto import router_producto
from api.router.router_detalle_pedido import router_detalle_pedido
from api.core.database import engine, Base

app = FastAPI()


# --- CONFIGURACIÓN DE CORS ---
origins = [
    "http://localhost:5173",    # Puerto de Vite (Frontend)
    "http://127.0.0.1:5173",    # Por si acaso usas React clásico
]

app.title = "Api service ECPedidos"
app.version = "0.0.1"

# Code First
Base.metadata.create_all(bind=engine)

app.include_router(router_pedido)
app.include_router(router_producto)
app.include_router(router_detalle_pedido)


app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_methods=["*"],
    allow_headers=["*"],
)

@app.get("/")
def root():
    return {"message": "API EcPedidos funcionando"}


@app.get('/', tags=['test'])
def message():
    return HTMLResponse('<h1>Aplicaciones Distribuidas -- Apis en Python</h1>')
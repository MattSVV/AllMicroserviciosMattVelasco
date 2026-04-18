const API_PYTHON_URL = "http://localhost:8000"; // Puerto de tu FastAPI

/**
 * Obtiene el catálogo de productos desde Python
 * GET /productos
 */
export const getProductos = async () => {
  try {
    const response = await fetch(`${API_PYTHON_URL}/productos`);
    if (!response.ok) throw new Error("Error al cargar productos");
    return await response.json();
  } catch (error) {
    console.error("Error en getProductos:", error);
    throw error;
  }
};

/**
 * Crea el pedido en Python (Este endpoint debe guardar en BD y enviar a RabbitMQ)
 * POST /pedidos
 */
export const createPedido = async (pedidoData) => {
  try {
    console.log("URL de destino:", `${API_PYTHON_URL}/pedidos`);
    console.log("Datos que enviamos:", JSON.stringify(pedidoData));
    // Usamos URL limpia
    const response = await fetch(`${API_PYTHON_URL}/pedidos/`, {
      method: "POST",
      headers: { 
        "Content-Type": "application/json",
        "Accept": "application/json" 
      },
      body: JSON.stringify(pedidoData),
    });
    if (!response.ok) throw new Error("Error al procesar el pedido");
    return await response.json();
  } catch (error) {
    console.error("Error en createPedido:", error);
    throw error;
  }
};
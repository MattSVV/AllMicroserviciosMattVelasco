const API_JAVA_URL = "http://localhost:8080/api/v1";

export const crearFactura = async (facturaData) => {
  try {
    const response = await fetch(`${API_JAVA_URL}/factura`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(facturaData),
    });
    if (!response.ok) throw new Error("Error al generar factura en Java");
    return await response.json();
  } catch (error) {
    console.error("Error en crearFactura:", error);
    throw error;
  }
};
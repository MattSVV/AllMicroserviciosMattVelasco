const API_URL = "https://localhost:7295/api";

// ========== CLIENTES ==========

/**
 * Obtiene la lista de todos los clientes
 * GET /api/clientes
 */
export const getClientes = async () => {
  try {
    const response = await fetch(`${API_URL}/Clientes`);
    if (!response.ok) {
      throw new Error(`Error al obtener clientes: ${response.statusText}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error en getClientes:", error);
    throw error;
  }
};

/**
 * Crea un nuevo cliente
 * POST /api/clientes
 * @param {Object} clienteData - Datos del cliente (nombre, email, telefono, etc)
 */
export const createCliente = async (clienteData) => {
  try {
    const response = await fetch(`${API_URL}/Clientes`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(clienteData),
    });
    if (!response.ok) {
      throw new Error(`Error al crear cliente: ${response.statusText}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error en createCliente:", error);
    throw error;
  }
};

/**
 * Actualiza un cliente existente
 * PUT /api/clientes/:id
 * @param {string|number} clienteId - ID del cliente a actualizar
 * @param {Object} clienteData - Datos actualizados del cliente
 */
export const updateCliente = async (clienteId, clienteData) => {
  try {
    const response = await fetch(`${API_URL}/Clientes/${clienteId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(clienteData),
    });

    if (!response.ok) {
      throw new Error(`Error al actualizar cliente: ${response.statusText}`);
    }

    // Algunas APIs de C# devuelven 204 No Content, si es así, manejamos la respuesta vacía
    if (response.status === 204) return { success: true };

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error en updateCliente:", error);
    throw error;
  }
};

/**
 * Elimina un cliente por ID
 * DELETE /api/clientes/:id
 * @param {string|number} clienteId - ID del cliente a eliminar
 */
export const deleteCliente = async (clienteId) => {
  try {
    const response = await fetch(`${API_URL}/Clientes/${clienteId}`, {
      method: "DELETE",
    });
    if (!response.ok) {
      throw new Error(`Error al eliminar cliente: ${response.statusText}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error en deleteCliente:", error);
    throw error;
  }
};

// ========== DIRECCIONES ==========

/**
 * Obtiene la lista de todas las direcciones
 * GET /api/direcciones
 */
export const getDirecciones = async () => {
  try {
    const response = await fetch(`${API_URL}/Direccion`);
    if (!response.ok) {
      throw new Error(`Error al obtener direcciones: ${response.statusText}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error en getDirecciones:", error);
    throw error;
  }
};

/**
 * Crea una nueva dirección
 * POST /api/direcciones
 * @param {Object} direccionData - Datos de la dirección (calle, numero, ciudad, etc)
 */
export const createDireccion = async (direccionData) => {
  try {
    const response = await fetch(`${API_URL}/Direccion`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(direccionData),
    });
    if (!response.ok) {
      throw new Error(`Error al crear dirección: ${response.statusText}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error en createDireccion:", error);
    throw error;
  }
};

/**
 * Actualiza una dirección existente
 * PUT /api/direccion/:id
 * @param {string|number} direccionId - ID de la dirección a actualizar
 * @param {Object} direccionData - Datos actualizados de la dirección
 */
export const updateDireccion = async (direccionId, direccionData) => {
  try {
    const response = await fetch(`${API_URL}/Direccion/${direccionId}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(direccionData),
    });

    if (!response.ok) {
      throw new Error(`Error al actualizar dirección: ${response.statusText}`);
    }

    if (response.status === 204) return { success: true };

    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error en updateDireccion:", error);
    throw error;
  }
};

/**
 * Elimina una dirección por ID
 * DELETE /api/direcciones/:id
 * @param {string|number} direccionId - ID de la dirección a eliminar
 */
export const deleteDireccion = async (direccionId) => {
  try {
    const response = await fetch(`${API_URL}/Direccion/${direccionId}`, {
      method: "DELETE",
    });
    if (!response.ok) {
      throw new Error(`Error al eliminar dirección: ${response.statusText}`);
    }
    const data = await response.json();
    return data;
  } catch (error) {
    console.error("Error en deleteDireccion:", error);
    throw error;
  }
};

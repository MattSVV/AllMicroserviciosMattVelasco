import { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { getProductos, createPedido } from "../services/pedidoService";

export default function Pedido() {
  const { state } = useLocation();
  const navigate = useNavigate();
  const cliente = state?.cliente; // Viene de C#

  const [productos, setProductos] = useState([]);
  const [carrito, setCarrito] = useState([]);
  const [direccionId, setDireccionId] = useState("");

  // Cargar productos de Python al entrar
  useEffect(() => {
    const fetchProds = async () => {
      const data = await getProductos();
      setProductos(data);
    };
    fetchProds();
  }, []);

  const agregarAlCarrito = (prod) => {
    // Evitar duplicados, solo aumentar cantidad si ya existe
    const existe = carrito.find(item => item.producto_id === prod.id);
    if (existe) {
      setCarrito(carrito.map(item => 
        item.producto_id === prod.id ? { ...item, cantidad: item.cantidad + 1, subtotal: (item.cantidad + 1) * prod.precio_unitario } : item
      ));
    } else {
      setCarrito([...carrito, { 
        producto_id: prod.id, 
        nombre: prod.nombre, 
        cantidad: 1, 
        precio: prod.precio_unitario,
        subtotal: prod.precio_unitario 
      }]);
    }
  };

  const handleFinalizarPedido = async () => {
    const payload = {
      direccion_cliente_id: parseInt(direccionId),
      detalles: carrito.map(item => ({
        producto_id: item.producto_id,
        cantidad: item.cantidad
      }))
    };

    try {
      // 1. Enviamos a Python
      const pedidoCreado = await createPedido(payload);
      
      const subtotalCarrito = carrito.reduce((acc, item) => {
  return acc + Number(item.subtotal || 0);
}, 0);

alert(`¡Pedido procesado! Subtotal calculado: ${subtotalCarrito}`);
      // 2. NAVEGACIÓN HACIA FACTURA
      // Pasamos el ID que nos dio Python, el subtotal y el nombre del cliente
      navigate("/factura", { 
        state: { 
          pedidoId: pedidoCreado.id, 
          subtotal: subtotalCarrito,
          clienteNombre: `${cliente.nombreCliente} ${cliente.apellidoCliente}`
        } 
      });

    } catch (err) {
      alert("Error al conectar con el microservicio de Python");
      console.error(err);
    }
  };

  return (
    <div className="p-10 bg-gray-900 min-h-screen text-white">
      <h1 className="text-3xl font-bold mb-8 text-indigo-400">Terminal de Ventas (POS)</h1>
      
      <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
        {/* COLUMNA 1: CLIENTE Y DIRECCION */}
        <div className="bg-gray-800 p-6 rounded-xl shadow-lg border border-gray-700">
          <h2 className="text-xl font-semibold mb-4 text-blue-400">1. Cliente de C#</h2>
          <div className="p-4 bg-gray-700 rounded-lg">
            <p className="font-bold">{cliente?.nombreCliente} {cliente?.apellidoCliente}</p>
            <p className="text-sm text-gray-400">ID: {cliente?.id}</p>
            
            <label className="block mt-4 mb-2 text-xs uppercase text-gray-500 font-bold">Seleccionar Dirección</label>
            <select 
              className="w-full p-2 bg-gray-900 border border-gray-600 rounded"
              onChange={(e) => setDireccionId(e.target.value)}
              value={direccionId}
            >
              <option value="">-- Seleccione --</option>
              {/* Aquí asumo que cliente trae sus direcciones de C# */}
              {cliente?.direcciones?.map(dir => (
                <option key={dir.id} value={dir.id}>{dir.calleDireccion} ({dir.ciudadDireccion})</option>
              ))}
            </select>
          </div>
        </div>

        {/* COLUMNA 2: PRODUCTOS DE PYTHON */}
        <div className="bg-gray-800 p-6 rounded-xl shadow-lg border border-gray-700">
          <h2 className="text-xl font-semibold mb-4 text-green-400">2. Productos de Python</h2>
          <div className="space-y-3">
            {productos.map(p => (
              <div key={p.id} className="flex justify-between items-center p-3 bg-gray-700 rounded-lg">
                <span>{p.nombre} - <span className="text-green-400 font-mono">${p.precio_unitario}</span></span>
                <button 
                  onClick={() => agregarAlCarrito(p)}
                  className="bg-indigo-600 px-3 py-1 rounded text-sm hover:bg-indigo-500"
                > + </button>
              </div>
            ))}
          </div>
        </div>

        {/* COLUMNA 3: RESUMEN Y ENVÍO */}
        <div className="bg-gray-800 p-6 rounded-xl shadow-lg border-t-4 border-indigo-500">
          <h2 className="text-xl font-semibold mb-4">3. Resumen</h2>
          <div className="min-h-[200px]">
            {carrito.map(item => (
              <div key={item.producto_id} className="flex justify-between text-sm mb-2 border-b border-gray-700 pb-1">
                <span>{item.nombre} x{item.cantidad}</span>
                <span className="font-mono">${Number(item.subtotal || 0).toFixed(2)}</span>
              </div>
            ))}
          </div>
          <button 
            disabled={!direccionId || carrito.length === 0}
            onClick={handleFinalizarPedido}
            className="w-full mt-6 bg-indigo-600 py-3 rounded-xl font-bold hover:bg-indigo-500 disabled:bg-gray-700 transition"
          >
            Generar Pedido & Facturar
          </button>
        </div>
      </div>
    </div>
  );
}
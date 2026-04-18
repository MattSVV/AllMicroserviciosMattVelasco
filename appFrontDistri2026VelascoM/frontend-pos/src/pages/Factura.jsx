import { useLocation, useNavigate } from "react-router-dom";
import { useState } from "react";
import { crearFactura } from "../services/facturaService";
import { CheckBadgeIcon, CreditCardIcon } from "@heroicons/react/24/solid";

export default function Factura() {
  const { state } = useLocation();
  const navigate = useNavigate();
  const { pedidoId, subtotal, clienteNombre } = state || {};

  const [metodoPago, setMetodoPago] = useState("EFECTIVO");
  const [pasoFinal, setPasoFinal] = useState(false);

  // Cálculos espejo de tu DTO de Java
    const subtotalNumerico = Number(subtotal || 0);  

  const porcentajeIva = 15;
  const valorIva = (subtotalNumerico * porcentajeIva) / 100;
  const totalFactura = subtotalNumerico + valorIva;

  const handleAceptar = async () => {
    const payload = {
  pedidoId: pedidoId,
  numeroFactura: pedidoId, // número automático
  metodoPago: metodoPago,
  iva: porcentajeIva, // 👈 nombre correcto
  estadoFactura: "PAGADA",
  fechaEmision: new Date().toISOString().split("T")[0]
};
    try {
      await crearFactura(payload);
      setPasoFinal(true); // Mostrar pantalla de éxito
    } catch (error) {
      alert("Error al guardar en la base de datos de Java");
    }
  };

  if (pasoFinal) return <PantallaExito />;

  return (
    <div className="min-h-screen bg-gray-900 flex items-center justify-center p-6 text-white">
      <div className="bg-white text-gray-800 p-8 rounded-3xl shadow-2xl w-full max-w-md border-t-8 border-orange-500">
        <h2 className="text-2xl font-black text-center mb-6 uppercase tracking-wider">Factura Electrónica</h2>
        
        <div className="border-b-2 border-dashed border-gray-300 pb-4 mb-4">
          <p className="text-sm text-gray-500 uppercase font-bold">Cliente</p>
          <p className="text-lg font-semibold">{clienteNombre}</p>
          <p className="text-xs text-gray-400">Pedido ID: #00{pedidoId}</p>
        </div>

        <div className="space-y-3 mb-8">
          <div className="flex justify-between">
  <span className="text-gray-600">Subtotal Pedido:</span>
  {/* CAMBIO: Usamos 'subtotal' directamente, no 'item.subtotal' */}
  <span className="font-mono font-bold">${Number(subtotal || 0).toFixed(2)}</span>
            </div>
          <div className="flex justify-between">
            <span className="text-gray-600">IVA ({porcentajeIva}%):</span>
            <span className="font-mono font-bold text-orange-600">+ ${valorIva.toFixed(2)}</span>
          </div>
          <div className="flex justify-between text-2xl border-t pt-4">
            <span className="font-black">TOTAL:</span>
            <span className="font-black text-indigo-700">${Number(totalFactura || 0).toFixed(2)}</span>
          </div>
        </div>

        <div className="mb-8">
          <label className="block text-xs font-bold text-gray-500 mb-2 uppercase">Método de Pago</label>
          <div className="grid grid-cols-2 gap-2">
            {["EFECTIVO", "TARJETA", "TRANSFERENCIA"].map((m) => (
              <button
                key={m}
                onClick={() => setMetodoPago(m)}
                className={`py-2 text-xs rounded-lg font-bold border-2 transition ${
                  metodoPago === m ? "border-indigo-600 bg-indigo-50 text-indigo-700" : "border-gray-100 text-gray-400"
                }`}
              >
                {m}
              </button>
            ))}
          </div>
        </div>

        <button 
          onClick={handleAceptar}
          className="w-full bg-orange-500 hover:bg-orange-600 text-white py-4 rounded-2xl font-black text-lg shadow-lg transform active:scale-95 transition"
        >
          CONFIRMAR Y GUARDAR
        </button>
      </div>
    </div>
  );
}

// Sub-componente de Éxito Final
function PantallaExito() {
  const navigate = useNavigate();
  return (
    <div className="min-h-screen bg-indigo-700 flex flex-col items-center justify-center p-6 text-white text-center">
      <CheckBadgeIcon className="w-32 h-32 text-green-400 mb-6 animate-bounce" />
      <h1 className="text-5xl font-black mb-4">¡FLUJO COMPLETO!</h1>
      <p className="text-indigo-200 max-w-md mb-10 text-lg">
        El cliente fue registrado en **C#**, el pedido se procesó en **Python** vía **RabbitMQ** y la factura se guardó exitosamente en **Java**.
      </p>
      <button 
        onClick={() => navigate("/")}
        className="bg-white text-indigo-700 px-10 py-4 rounded-full font-black hover:bg-indigo-100 transition shadow-2xl"
      >
        VOLVER AL INICIO
      </button>
    </div>
  );
}
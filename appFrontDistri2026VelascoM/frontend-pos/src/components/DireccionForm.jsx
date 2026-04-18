import { useState } from "react";

export default function DireccionForm({ cliente, direcciones, onCreateDir, onDeleteDir }) {
  const [formData, setFormData] = useState({
    provinciaDireccion: "",
    ciudadDireccion: "",
    calleDireccion: "",
    codigoPostalDireccion: ""
  });

  const handleAdd = (e) => {
    e.preventDefault();
    // Inyectamos el idCliente que requiere tu DTO de C#
    onCreateDir({ ...formData, idCliente: cliente.id });
    setFormData({ provinciaDireccion: "", ciudadDireccion: "", calleDireccion: "", codigoPostalDireccion: "" });
  };

  return (
    <div className="mt-10 bg-white p-6 rounded-xl shadow-2xl">
      <h2 className="text-xl font-bold mb-4 text-gray-800">Direcciones de {cliente.nombreCliente}</h2>
      
      <form onSubmit={handleAdd} className="grid grid-cols-2 gap-3 mb-6">
        <input type="text" placeholder="Provincia" className="border p-2 rounded" required
          value={formData.provinciaDireccion} onChange={(e) => setFormData({...formData, provinciaDireccion: e.target.value})} />
        
        <input type="text" placeholder="Ciudad" className="border p-2 rounded" required
          value={formData.ciudadDireccion} onChange={(e) => setFormData({...formData, ciudadDireccion: e.target.value})} />
        
        <input type="text" placeholder="Calle" className="border p-2 rounded col-span-2" required
          value={formData.calleDireccion} onChange={(e) => setFormData({...formData, calleDireccion: e.target.value})} />
        
        <input type="text" placeholder="Código Postal" className="border p-2 rounded" required
          value={formData.codigoPostalDireccion} onChange={(e) => setFormData({...formData, codigoPostalDireccion: e.target.value})} />
        
        <button type="submit" className="bg-green-600 text-white px-4 py-2 rounded font-bold">
          Agregar Dirección
        </button>
      </form>

      <div className="space-y-2">
        {direcciones.map((dir) => (
          <div key={dir.id} className="flex justify-between items-center bg-gray-100 p-3 rounded border-l-4 border-green-500">
            <div>
              <p className="font-bold">{dir.calleDireccion}</p>
              <p className="text-sm text-gray-500">{dir.ciudadDireccion}, {dir.provinciaDireccion} (CP: {dir.codigoPostalDireccion})</p>
            </div>
            <button onClick={() => onDeleteDir(dir.id)} className="text-red-600 font-bold hover:underline">
              Eliminar
            </button>
          </div>
        ))}
      </div>
    </div>
  );
}
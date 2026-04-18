import { useState, useEffect } from "react";

export default function ClienteForm({ onClienteCreated, selectedCliente, onCancelEdit, onUpdateCliente }) {
  const [formData, setFormData] = useState({
    nombreCliente: "",
    apellidoCliente: "",
    emailCliente: "",
    cedulaCliente: "",
    telefonoCliente: "",
    fechaNacimientoCliente: ""
  });

  useEffect(() => {
    if (selectedCliente) {
      // Formatear fecha para el input type="date" (YYYY-MM-DD)
      const fecha = selectedCliente.fechaNacimientoCliente?.split('T')[0] || "";
      setFormData({ ...selectedCliente, fechaNacimientoCliente: fecha });
    } else {
      setFormData({
        nombreCliente: "", apellidoCliente: "", emailCliente: "",
        cedulaCliente: "", telefonoCliente: "", fechaNacimientoCliente: ""
      });
    }
  }, [selectedCliente]);

  const handleSubmit = (e) => {
    e.preventDefault();
    if (selectedCliente) {
      onUpdateCliente(selectedCliente.id, formData);
    } else {
      onClienteCreated(formData);
    }
  };

  return (
    <form onSubmit={handleSubmit} className="bg-gray-700 p-6 rounded-xl shadow-lg mb-6 grid grid-cols-2 gap-4">
      <h2 className="text-xl font-bold text-white col-span-2">
        {selectedCliente ? "Editar Cliente" : "Nuevo Cliente"}
      </h2>
      
      <input type="text" placeholder="Nombre" className="p-2 rounded" required
        value={formData.nombreCliente} onChange={(e) => setFormData({...formData, nombreCliente: e.target.value})} />
      
      <input type="text" placeholder="Apellido" className="p-2 rounded" required
        value={formData.apellidoCliente} onChange={(e) => setFormData({...formData, apellidoCliente: e.target.value})} />
      
      <input type="email" placeholder="Email" className="p-2 rounded" required
        value={formData.emailCliente} onChange={(e) => setFormData({...formData, emailCliente: e.target.value})} />
      
      <input type="text" placeholder="Cédula" className="p-2 rounded" required
        value={formData.cedulaCliente} onChange={(e) => setFormData({...formData, cedulaCliente: e.target.value})} />
      
      <input type="text" placeholder="Teléfono" className="p-2 rounded" required
        value={formData.telefonoCliente} onChange={(e) => setFormData({...formData, telefonoCliente: e.target.value})} />
      
      <input type="date" className="p-2 rounded" required
        value={formData.fechaNacimientoCliente} onChange={(e) => setFormData({...formData, fechaNacimientoCliente: e.target.value})} />

      <div className="col-span-2 flex gap-2">
        <button type="submit" className="bg-blue-600 text-white px-6 py-2 rounded font-bold hover:bg-blue-500">
          {selectedCliente ? "Actualizar Cliente" : "Guardar Cliente"}
        </button>
        {selectedCliente && (
          <button type="button" onClick={onCancelEdit} className="bg-gray-400 text-white px-6 py-2 rounded">
            Cancelar
          </button>
        )}
      </div>
    </form>
  );
}
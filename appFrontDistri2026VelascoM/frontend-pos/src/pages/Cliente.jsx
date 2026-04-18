import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom"; // Importante para la navegación
import { getClientes, createCliente, deleteCliente, updateCliente, getDirecciones, createDireccion, deleteDireccion } from "../services/clienteService";
import ClienteForm from "../components/ClienteForm";
import ClienteList from "../components/ClienteList";
import DireccionForm from "../components/DireccionForm";

export default function Clientes() {
  const navigate = useNavigate(); // Inicializamos el hook de navegación
  const [clientes, setClientes] = useState([]);
  const [selectedCliente, setSelectedCliente] = useState(null); 
  const [editingCliente, setEditingCliente] = useState(null); 
  const [direcciones, setDirecciones] = useState([]);

  useEffect(() => { loadClientes(); }, []);

  const loadClientes = async () => { setClientes(await getClientes()); };

  const handleCreate = async (data) => { await createCliente(data); loadClientes(); };
  
  const handleUpdate = async (id, data) => { 
    await updateCliente(id, data); 
    setEditingCliente(null); 
    loadClientes(); 
  };

  const handleDelete = async (id) => { await deleteCliente(id); loadClientes(); };

  const handleSelect = async (cliente) => {
    setSelectedCliente(cliente);
    // Cargamos las direcciones de este cliente desde la API de C#
    const data = await getDirecciones(cliente.id);
    setDirecciones(data);
  };

  // Función para saltar al Microservicio de Python
  const handleIrAPedido = () => {
    if (selectedCliente) {
      // Pasamos el cliente y sus direcciones al estado de la siguiente ruta
      navigate("/pedido", { 
        state: { 
          cliente: {
            ...selectedCliente,
            direcciones: direcciones // Enviamos las direcciones cargadas
          } 
        } 
      });
    }
  };

  return (
    <div className="p-10 min-h-screen bg-gray-800">
      <div className="flex justify-between items-center mb-6">
        <h1 className="text-3xl font-bold text-gray-300">1. Gestión de Clientes (C#)</h1>
        
        {/* BOTÓN DE FLUJO: Solo se activa si hay un cliente seleccionado */}
        {selectedCliente && (
          <button 
            onClick={handleIrAPedido}
            className="bg-indigo-600 hover:bg-indigo-500 text-white px-6 py-3 rounded-xl font-bold shadow-lg animate-pulse transition"
          >
            Continuar a Pedido 🛒
          </button>
        )}
      </div>
      
      <ClienteForm 
        onClienteCreated={handleCreate} 
        onUpdateCliente={handleUpdate}
        selectedCliente={editingCliente}
        onCancelEdit={() => setEditingCliente(null)}
      />

      <div className="mb-10">
        <h2 className="text-xl text-gray-400 mb-4 font-semibold">Lista de Clientes</h2>
        <ClienteList 
          clientes={clientes} 
          onSelect={handleSelect} 
          onDelete={handleDelete} 
          onEdit={(c) => setEditingCliente(c)}
        />
      </div>

      {selectedCliente && (
        <div className="border-t border-gray-700 pt-8">
          <DireccionForm 
            cliente={selectedCliente} 
            direcciones={direcciones} 
            onCreateDir={async (d) => { 
              await createDireccion(d); 
              const actualizadas = await getDirecciones(selectedCliente.id);
              setDirecciones(actualizadas);
            }}
            onDeleteDir={async (id) => { 
              await deleteDireccion(id); 
              const actualizadas = await getDirecciones(selectedCliente.id);
              setDirecciones(actualizadas);
            }}
          />
        </div>
      )}
    </div>
  );
}
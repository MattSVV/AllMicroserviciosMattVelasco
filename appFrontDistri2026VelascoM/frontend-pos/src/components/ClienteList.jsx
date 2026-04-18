export default function ClienteList({ clientes, onSelect, onDelete, onEdit }) {
  return (
    <div className="grid md:grid-cols-2 gap-6">
      {clientes.map((cliente) => (
        <div key={cliente.id} className="bg-white p-5 rounded-xl shadow-md border-t-4 border-blue-600">
          <div className="flex justify-between items-start">
            <div>
              <h3 className="font-bold text-lg">{cliente.nombreCliente} {cliente.apellidoCliente}</h3>
              <p className="text-sm text-gray-500">CI: {cliente.cedulaCliente}</p>
              <p className="text-blue-600 text-sm">{cliente.emailCliente}</p>
            </div>
          </div>
          
          <div className="mt-4 flex gap-2">
            <button onClick={() => onSelect(cliente)} className="bg-gray-800 text-white px-3 py-1 rounded text-sm">
              Direcciones
            </button>
            <button onClick={() => onEdit(cliente)} className="bg-yellow-500 text-white px-3 py-1 rounded text-sm">
              Editar
            </button>
            <button onClick={() => onDelete(cliente.id)} className="bg-red-600 text-white px-3 py-1 rounded text-sm">
              Eliminar
            </button>
          </div>
        </div>
      ))}
    </div>
  );
}
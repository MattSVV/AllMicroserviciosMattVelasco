import { useNavigate } from "react-router-dom";
import { UserGroupIcon, ShoppingCartIcon, DocumentTextIcon, ServerIcon } from "@heroicons/react/24/outline";

function Home() {
  const navigate = useNavigate();

  const modules = [
    {
      title: "Gestión de Clientes",
      subtitle: "Microservicio C# / .NET",
      description: "Registro de clientes y sus múltiples direcciones de entrega.",
      icon: <UserGroupIcon className="w-8 h-8 text-blue-500" />,
      path: "/clientes",
      color: "border-blue-500",
      tech: "ASP.NET Core API"
    },
    {
      title: "Flujo de Pedido",
      subtitle: "Microservicio Python / Flask",
      description: "Toma de pedidos y envío de eventos mediante RabbitMQ.",
      icon: <ShoppingCartIcon className="w-8 h-8 text-green-500" />,
      path: "/pedido",
      color: "border-green-500",
      tech: "Python + RabbitMQ"
    },
    {
      title: "Facturación",
      subtitle: "Microservicio Java / Spring Boot",
      description: "Generación automática de facturas y cálculos de impuestos.",
      icon: <DocumentTextIcon className="w-8 h-8 text-orange-500" />,
      path: "/factura",
      color: "border-orange-500",
      tech: "Spring Boot + JPA"
    }
  ];

  return (
    <div className="min-h-screen bg-gray-900 flex flex-col items-center justify-center p-6">
      {/* Header */}
      <div className="text-center mb-12">
        <h1 className="text-4xl font-extrabold text-white mb-2 tracking-tight">
          Sistema POS Distribuido
        </h1>
        <div className="flex items-center justify-center gap-2 text-indigo-400">
          <ServerIcon className="w-5 h-5" />
          <span className="uppercase tracking-widest text-sm font-semibold">Arquitectura de Microservicios</span>
        </div>
      </div>

      {/* Grid de Módulos */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-8 max-w-6xl w-full">
        {modules.map((module, index) => (
          <div 
            key={index}
            onClick={() => navigate(module.path)}
            className={`bg-gray-800 border-t-4 ${module.color} p-8 rounded-2xl shadow-xl hover:transform hover:-translate-y-2 transition-all duration-300 cursor-pointer group`}
          >
            <div className="bg-gray-700 w-16 h-16 rounded-lg flex items-center justify-center mb-6 group-hover:scale-110 transition-transform">
              {module.icon}
            </div>
            
            <h2 className="text-2xl font-bold text-white mb-1">{module.title}</h2>
            <p className="text-indigo-400 text-sm font-medium mb-4">{module.subtitle}</p>
            <p className="text-gray-400 text-sm mb-6 leading-relaxed">
              {module.description}
            </p>

            <div className="pt-4 border-t border-gray-700 flex items-center justify-between">
              <span className="text-xs font-mono text-gray-500 uppercase">{module.tech}</span>
              <span className="text-indigo-500 font-bold group-hover:translate-x-2 transition-transform">→</span>
            </div>
          </div>
        ))}
      </div>

      {/* Footer / Status Bar */}
      <div className="mt-16 text-gray-500 text-xs flex gap-6">
        <div className="flex items-center gap-1">
          <div className="w-2 h-2 bg-green-500 rounded-full animate-pulse"></div>
          Node.js Frontend Online
        </div>
        <span>|</span>
        <span>Distributed Systems 2026</span>
      </div>
    </div>
  );
}

export default Home;
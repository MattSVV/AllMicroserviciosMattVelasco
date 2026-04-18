import { Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import Pedido from "./pages/Pedido";
import Cliente from "./pages/Cliente";
import Factura from "./pages/Factura";

function App() {
  return (
    <div className="min-h-screen bg-gray-100">
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/pedido" element={<Pedido />} />
        <Route path="/clientes" element={<Cliente />} />
        <Route path="/factura" element={<Factura />} />
      </Routes>
    </div>
  );
}

export default App;
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Landing from "./pages/Landing";
import Login from "./pages/Login";
import Dashboard from "./pages/Dashboard";
import Inventory from "./pages/Inventory";
import PlaceOrder from "./pages/PlaceOrder";
import Vehicles from "./pages/Vehicles";
import Warehouses from "./pages/Warehouses";
import Register from "./pages/Register";

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/inventory" element={<Inventory />} />
        <Route path="/placeorder" element={<PlaceOrder />} />
        <Route path="/trackorder" element={<TrackOrder />} />
        <Route path="/register" element={<Register />} />
        <Route path="/vehicles" element={<Vehicles />} />
        <Route path="/warehouses" element={<Warehouses />} />
      </Routes>
    </BrowserRouter>
  );
}

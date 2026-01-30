// import { Link } from "react-router-dom";
// import { useAuth } from "../auth/AuthContext";
import React from 'react';

export default function Navbar() {
    // const { logout } = useAuth();

    return (
        <nav className="flex justify-between items-center px-6 py-4 bg-black/60">
            <h1 className="text-xl font-bold text-cyan-400">LogiGraph</h1>
            <div className="space-x-4">
                <Link to="/dashboard">Dashboard</Link>
                <Link to="/order">Place Order</Link>
                <Link to="/track">Track</Link>
                <Link to="/warehouses">Warehouses</Link>
                <button onClick={logout} className="text-red-400">Logout</button>
            </div>
        </nav>
    );
}



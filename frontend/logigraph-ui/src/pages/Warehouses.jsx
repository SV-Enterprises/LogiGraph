import { useEffect, useState } from "react";
import api from "../api/api";

export default function Warehouses() {
    const [warehouses, setWarehouses] = useState([]);

    useEffect(() => {
        api.get("/warehouses").then(res => setWarehouses(res.data));
    }, []);

    return (
        <ul>
            {warehouses.map(w => (
                <li key={w.id}>
                    {w.name} ({w.latitude}, {w.longitude})
                </li>
            ))}
        </ul>
    );
}

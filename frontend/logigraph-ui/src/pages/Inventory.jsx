import { useState } from "react";
import api from "../api/api";

export default function Inventory() {
    const [wid, setWid] = useState("");
    const [items, setItems] = useState([]);

    const load = async () => {
        const res = await api.get(`/manager/inventory/warehouse/${wid}`);
        setItems(res.data);
    };

    return (
        <div>
            <input placeholder="Warehouse ID"
                onChange={e => setWid(e.target.value)} />
            <button onClick={load}>Load</button>
            <pre>{JSON.stringify(items, null, 2)}</pre>
        </div>
    );
}

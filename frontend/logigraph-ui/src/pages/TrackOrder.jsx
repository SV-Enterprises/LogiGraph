import { useState } from "react";
import api from "../api/api";

export default function TrackOrder() {
    const [trackingId, setId] = useState("");
    const [order, setOrder] = useState(null);

    const track = async () => {
        const res = await api.get(`/orders/${trackingId}`);
        setOrder(res.data);
    };

    return (
        <div>
            <input placeholder="Tracking ID"
                onChange={e => setId(e.target.value)} />
            <button onClick={track}>Track</button>
            {order && <pre>{JSON.stringify(order, null, 2)}</pre>}
        </div>
    );
}

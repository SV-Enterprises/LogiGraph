import { useState } from "react";
import api from "../api/api";
import GlassCard from "../components/GlassCard";

export default function PlaceOrder() {
    const [productId, setPid] = useState("");
    const [qty, setQty] = useState(1);

    const placeOrder = async () => {
        await api.post("/customer/orders", null, {
            params: {
                customerId: 1,   // TODO: decode JWT later
                productId,
                quantity: qty,
                destLat: 22.57,
                destLng: 88.36
            }
        });
        alert("Order placed successfully");
    };

    return (
        <GlassCard>
            <h2 className="text-xl mb-3">Place Order</h2>
            <input placeholder="Product ID"
                onChange={e => setPid(e.target.value)} />
            <input type="number"
                placeholder="Quantity"
                onChange={e => setQty(e.target.value)} />
            <button onClick={placeOrder}
                className="mt-3 bg-green-500 px-4 py-2 rounded">
                Place
            </button>
        </GlassCard>
    );
}

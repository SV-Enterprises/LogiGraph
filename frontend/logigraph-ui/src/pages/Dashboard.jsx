import { motion } from "framer-motion";
// import Navbar from "../components/Navbar";

export default function Dashboard() {
    return (
        <div className="p-10 grid grid-cols-1 md:grid-cols-3 gap-6">
            {[
                "Warehouses",
                "Inventory",
                "Orders",
                "Routing Graph",
                "Vehicles",
                "Tracking"
            ].map(title => (
                <motion.div
                    key={title}
                    whileHover={{ scale: 1.05 }}
                    className="glass p-6 rounded-xl"
                >
                    <h3 className="text-xl text-cyan-300">{title}</h3>
                    <p className="opacity-70 mt-2">
                        Manage {title.toLowerCase()}
                    </p>
                </motion.div>
            ))}
        </div>
    );
}

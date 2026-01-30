import { motion } from "framer-motion";

export default function GlassCard({ children }) {
    return (
        <motion.div
            initial={{ opacity: 0, y: 30 }}
            animate={{ opacity: 1, y: 0 }}
            transition={{ duration: 0.6 }}
            className="glass rounded-2xl p-8 w-full max-w-md"
        >
            {children}
        </motion.div>
    );
}

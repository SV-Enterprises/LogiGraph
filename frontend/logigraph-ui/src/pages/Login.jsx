import { useState } from "react";
import { useAuth } from "../auth/AuthContext";
import GlassCard from "../components/GlassCard";
import Hero3D from "../three/Hero3D";
import Navbar from "../components/Navbar";

export default function Login() {
    const { login } = useAuth();
    const [username, setU] = useState("");
    const [password, setP] = useState("");

    return (
        <div className="relative h-screen w-screen flex items-center justify-center overflow-hidden bg-black">
            {/* 3D Background */}
            <Hero3D />

            {/* Glass Login */}
            <div className="relative z-10">
                <GlassCard>
                    <h1 className="text-3xl font-bold text-cyan-400 mb-6 text-center">
                       Login to LogiGraph
                    </h1>

                    <input
                        className="w-full mb-4 p-3 rounded bg-black/40 text-white outline-none"
                        placeholder="Username"
                        onChange={(e) => setU(e.target.value)}
                    />

                    <input
                        type="password"
                        className="w-full mb-6 p-3 rounded bg-black/40 text-white outline-none"
                        placeholder="Password"
                        onChange={(e) => setP(e.target.value)}
                    />

                    <button
                        onClick={() => login(username, password)}
                        className="w-full bg-cyan-500 hover:bg-cyan-400 py-3 rounded-lg font-semibold shadow-lg shadow-cyan-500/50"
                    >
                        Login
                    </button>
                </GlassCard>
            </div>
        </div>
    );
}

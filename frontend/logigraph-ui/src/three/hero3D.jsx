import { Canvas } from "@react-three/fiber";
import { Float, OrbitControls, Sphere } from "@react-three/drei";

export default function Hero3D() {
    return (
        <Canvas
            camera={{ position: [0, 0, 8] }}
            style={{ position: "absolute", inset: 0, zIndex: 0 }}
        >
            <ambientLight intensity={0.6} />
            <directionalLight position={[5, 5, 5]} intensity={1} />

            <Float speed={2} rotationIntensity={1} floatIntensity={2}>
                <Sphere args={[2.5, 64, 64]}>
                    <meshStandardMaterial
                        color="#06b6d4"
                        wireframe
                        emissive="#22d3ee"
                    />
                </Sphere>
            </Float>

            <OrbitControls enableZoom={false} enablePan={false} />
        </Canvas>
    );
}

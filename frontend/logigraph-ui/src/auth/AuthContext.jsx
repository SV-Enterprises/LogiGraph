// import { createContext, useContext, useState } from "react";
// import api from "../api/api";

// const AuthContext = createContext();

// export function AuthProvider({ children }) {
//     const [token, setToken] = useState(
//         localStorage.getItem("token")
//     );

//     const login = async (username, password) => {
//         const res = await api.post("/auth/login", { username, password });
//         localStorage.setItem("token", res.data.token);
//         setToken(res.data.token);
//     };

//     const logout = () => {
//         localStorage.removeItem("token");
//         setToken(null);
//     };

//     return (
//         <AuthContext.Provider value={{ token, login, logout }}>
//             {children}
//         </AuthContext.Provider>
//     );
// }

// export const useAuth = () => useContext(AuthContext);

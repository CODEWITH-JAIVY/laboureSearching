import { createContext, useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import api from "../utils/axios";

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const navigate = useNavigate();
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  // 🔄 Fetch logged-in user from backend
  const refreshUser = async () => {
    try {
      const res = await api.get("/user/profile/me");
      setUser(res.data);
      return res.data;
    } catch (err) { 
      setUser(null );
      return null;
    } finally {
      setLoading(false);
    }
  };

  // 🔐 SINGLE SOURCE OF TRUTH LOGIN HANDLER
  const loginAndRedirect = async ({ token }) => {
    localStorage.setItem("token", token);

    const user = await refreshUser();

    // ❌ Role not selected
    if (!user?.userType) {
      navigate("/select-role");
      return;
    }

    // 👷 LABOUR
    if (user.userType === "LABOUR") {
      if (!user.profileExists) {
        navigate("/labour/create-profile");
      } else {
        navigate("/labour/dashboard");
      }
      return;
    }

    // 🧑 CUSTOMER
    if (user.userType === "CUSTOMER") {
      if (!user.profileExists) {
        navigate("/customer/create-profile");
      } else {
        navigate("/customer/dashboard");
      }
    }
  };

  // 🔁 App reload / refresh
  useEffect(() => {
    const token = localStorage.getItem("token");
    if (!token) {
      setLoading(false);
      return; 
    }
    refreshUser();
  }, []);

  return (
    <AuthContext.Provider
      value={{
        user,
        loading,
        loginAndRedirect,
        refreshUser,
      }}
    >
      {children}
    </AuthContext.Provider> 
  );
}

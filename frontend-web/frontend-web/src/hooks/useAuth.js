import { useContext } from "react";
import { AuthContext } from "../context/AuthContext";

export default function useAuth() {
  const { user, login, logout } = useContext(AuthContext);

  return {
    user,
    isLoggedIn: !!user,
    role: user?.role,
    login,
    logout,
  };
}

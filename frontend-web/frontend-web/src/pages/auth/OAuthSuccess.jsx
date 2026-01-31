import { useEffect, useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";

export default function OAuthSuccess() {
  const navigate = useNavigate();
  const { loginAndRedirect } = useContext(AuthContext);

  useEffect(() => {
    const token = new URLSearchParams(window.location.search).get("token");

    if (!token) {
      navigate("/login");
      return;
    }

    // ✅ ONLY TOKEN PASS KARO
    loginAndRedirect({ token });
  }, []);

  return <p className="text-center mt-10">Logging in...</p>;
}

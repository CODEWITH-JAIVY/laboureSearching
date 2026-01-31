import { useContext, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../context/AuthContext";

export default function CreateProfile() {
  const { user } = useContext(AuthContext);
  const navigate = useNavigate();

  useEffect(() => {
    if (!user) return;

    //  role not selected
    if (!user.userType) {
      navigate("/select-role");
      return;
    }

    // 👷 LABOUR
    if (user.userType === "LABOUR") {
      navigate("/labour/create-profile");
      return;
    }

    // 🧑‍💼 CUSTOMER
    if (user.userType === "CUSTOMER") {
      navigate("/customer/create-profile");
    }
  }, [user]);

  return (
    <div className="flex justify-center items-center min-h-[60vh]">
      <p className="text-gray-600">Redirecting to profile setup...</p>
    </div>
  );
}

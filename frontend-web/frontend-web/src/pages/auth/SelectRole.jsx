import { useContext } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import { setUserTypeApi } from "../../api/user.api";

export default function SelectRole() {
  const { refreshUser, loginAndRedirect } = useContext(AuthContext);
  const navigate = useNavigate();

  const selectRole = async (role) => {
    console.log("rol of  the use ", role);
    // console.log(role.selectRole);
    try {
      // 1️⃣ backend call
      console.log("in the try block  before setUSerApi ", role);
      await setUserTypeApi(role);
      console.log("in the try block  after setUSerApi ", role);
      // 2️⃣ get updated user from backend
      const updatedUser = await refreshUser();
      console.log(updatedUser, "Set updatedUser  before return  ");
      // 3️⃣ NOW decide redirect (CRITICAL)
      if (!updatedUser) return;

      console.log("After Return ");
      console.log(updatedUser.userType, "User Type set ");

      if (updatedUser.userType === "LABOUR") {
        console.log("Laboure profil  if block  ");
        if (updatedUser.labourProfile) {
          console.log("laboure Dashboard called ");
          navigate("/labour/dashboard");
        } else {
          console.log("laboure create profile  called ");
          navigate("/labour/create-profile");
        }
      }

      if (updatedUser.userType === "CUSTOMER") {
        console.log("Customer  if block call ");
        if (updatedUser.customerProfile) {
          navigate("/customer/dashboard");
        } else {
          navigate("/customer/create-profile");
        }
      }
    } catch (e) {
      console.error(e);
      alert(role, "already set ", e);
    }
  };

  return (
    <div className="flex justify-center items-center min-h-[70vh]">
      <div className="bg-white p-6 rounded-lg shadow w-full max-w-md text-center">
        <h2 className="text-xl font-bold mb-6">Aap kya karna chahte ho?</h2>

        <button
          onClick={() => selectRole("LABOUR")}
          className="w-full bg-blue-600 text-white py-3 rounded mb-4"
        >
          👷 Mujhe kaam chahiye
        </button>

        <button
          onClick={() => selectRole("CUSTOMER")}
          className="w-full bg-green-600 text-white py-3 rounded"
        >
          🙋 Mujhe labour chahiye
        </button>
      </div>
    </div>
  );
}

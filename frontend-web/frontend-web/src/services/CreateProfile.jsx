import { useNavigate } from "react-router-dom";
import {
  createLabourProfileService,
  createCustomerProfileService,
} from "../services/profile-create.service";

export default function CreateProfile() {
  const navigate = useNavigate();

  const createLabour = async () => {
    try {
      await createLabourProfileService();
      navigate("/profile");
    } catch {
      alert("Failed to create labour profile");
    }
  };

  const createCustomer = async () => {
    try {
      await createCustomerProfileService();
      navigate("/profile");
    } catch {
      alert("Failed to create customer profile");
    }
  };

  return (
    <div className="flex justify-center items-center min-h-[60vh]">
      <div className="bg-white dark:bg-gray-900 p-6 rounded-xl shadow w-full max-w-md">
        <h2 className="text-xl font-bold text-center mb-6">
          Aap kya karna chahte ho?
        </h2>

        <button
          onClick={createLabour}
          className="w-full bg-blue-600 text-white py-3 rounded mb-4"
        >
          👷 Mujhe kaam chahiye
        </button>

        <button
          onClick={createCustomer}
          className="w-full bg-green-600 text-white py-3 rounded"
        >
          🧑‍💼 Mujhe labour chahiye
        </button>
      </div>
    </div>
  );
}

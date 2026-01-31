import { useContext, useState } from "react";
import { AuthContext } from "../../context/AuthContext";
import { loginUserService } from "../../services/auth.service";
import { FcGoogle } from "react-icons/fc";

export default function Login() {
  const { loginAndRedirect } = useContext(AuthContext);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const res = await loginUserService({ email, password });

      // ✅ ONLY TOKEN
      loginAndRedirect({ token: res.token });
    } catch {
      setError("Invalid email or password");
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gray-100">
      <div className="bg-white p-6 rounded-xl shadow-md w-full max-w-sm">
        <h2 className="text-2xl font-bold text-center mb-4">
          Login to <span className="text-green-600">KaamSetu</span>
        </h2>

        {error && (
          <p className="bg-red-100 text-red-600 p-2 rounded mb-3 text-sm">
            {error}
          </p>
        )}

        <button
          onClick={() =>
            (window.location.href =
              "http://localhost:8080/oauth2/authorization/google")
          }
          className="w-full flex items-center justify-center gap-2 border py-2 rounded mb-4"
        >
          <FcGoogle size={22} /> Continue with Google
        </button>

        <form onSubmit={handleSubmit} className="space-y-3">
          <input
            className="w-full border px-3 py-2 rounded"
            placeholder="Email"
            onChange={(e) => setEmail(e.target.value)}
          />

          <input
            type="password"
            className="w-full border px-3 py-2 rounded"
            placeholder="Password"
            onChange={(e) => setPassword(e.target.value)}
          />

          <button className="w-full bg-green-600 text-white py-2 rounded">
            Login
          </button>
        </form>
      </div>
    </div>
  );
}

import { useState, useContext } from "react";
import { AuthContext } from "../../context/AuthContext";
import { signupWithEmail } from "../../api/auth.api";

export default function Signup() {
  const { loginAndRedirect } = useContext(AuthContext);

  const [form, setForm] = useState({
    name: "",
    email: "",
    mobile: "",
    password: "",
  });

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSignup = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);

    try {
      const res = await signupWithEmail(form);

      /**
       * ✅ SINGLE SOURCE OF TRUTH
       * Backend response decides everything:
       * {
       *   token,
       *   profileExists,
       *   userType
       * }
       */
      loginAndRedirect(res.data);
    } catch (err) {
      setError(
        err?.response?.data?.message ||
          err?.response?.data ||
          "Signup failed. Check details.",
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="flex justify-center items-center min-h-[70vh]">
      <form
        onSubmit={handleSignup}
        className="bg-white p-6 rounded-lg w-full max-w-md shadow"
      >
        <h2 className="text-xl font-bold mb-4 text-center">Create Account</h2>

        {error && <p className="text-red-600 mb-3 text-sm">{error}</p>}

        <input
          name="mobile"
          placeholder="Mobile Number"
          value={form.mobile}
          onChange={handleChange}
          className="w-full border p-2 mb-3 rounded"
          required
        />

        <input
          name="name"
          placeholder="Full Name"
          value={form.name}
          onChange={handleChange}
          className="w-full border p-2 mb-3 rounded"
          required
        />

        <input
          name="email"
          type="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
          className="w-full border p-2 mb-3 rounded"
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          className="w-full border p-2 mb-4 rounded"
          required
        />

        <button
          disabled={loading}
          className="w-full bg-blue-600 text-white py-2 rounded disabled:opacity-60"
        >
          {loading ? "Creating..." : "Sign Up"}
        </button>
      </form>
    </div>
  );
}

import { useContext, useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "../../context/AuthContext";
import axios from "../../utils/axios";

export default function Dashboard() {
  const navigate = useNavigate();
  const { user, logout } = useContext(AuthContext);

  const [loading, setLoading] = useState(true);
  const [profile, setProfile] = useState(null);
  const [error, setError] = useState("");

  // 🔐 Auth Guard
  useEffect(() => {
    if (!user) {
      navigate("/login");
      return;
    }

    loadProfile();
    // eslint-disable-next-line
  }, []);

  // ================= LOAD PROFILE =================
  const loadProfile = async () => {
    try {
      const res = await axios.get("/user/profile/me");
      setProfile(res.data);
    } catch (err) {
      setError("Profile load failed", err);
    } finally {
      setLoading(false);
    }
  };

  // ================= LOGOUT =================
  const handleLogout = () => {
    logout();
    navigate("/login");
  };

  // ================= UI STATES =================
  if (loading) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <p className="text-gray-500">Loading dashboard...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex justify-center items-center min-h-screen">
        <p className="text-red-500">{error}</p>
      </div>
    );
  }

  // ================= MAIN DASHBOARD =================
  return (
    <div className="min-h-screen bg-gray-100 p-4">
      {/* HEADER */}
      <div className="flex justify-between items-center bg-white p-4 rounded shadow mb-4">
        <div>
          <h2 className="text-xl font-bold">
            Welcome, {user.username || "User"} 👋
          </h2>
          <p className="text-sm text-gray-500">
            Role: {user.userType || "Not selected"}
          </p>
        </div>

        <button
          onClick={handleLogout}
          className="bg-red-500 text-white px-4 py-2 rounded"
        >
          Logout
        </button>
      </div>

      {/* PROFILE CARD */}
      <div className="bg-white p-4 rounded shadow">
        <h3 className="text-lg font-semibold mb-3">Your Profile</h3>

        {profile?.profileDto ? (
          <div className="space-y-2">
            <p>
              <b>Name:</b> {profile.profileDto.name}
            </p>
            <p>
              <b>Email:</b> {profile.profileDto.email}
            </p>
            <p>
              <b>Mobile:</b> {profile.profileDto.mobile}
            </p>

            {profile.profileDto.labourType && (
              <>
                <p>
                  <b>Labour Type:</b> {profile.profileDto.labourType}
                </p>
                <p>
                  <b>Working Hours:</b> {profile.profileDto.workingHours}
                </p>
              </>
            )}
          </div>
        ) : (
          <p className="text-gray-500">No profile data</p>
        )}
      </div>

      {/* ACTIONS */}
      <div className="mt-6 grid grid-cols-1 md:grid-cols-2 gap-4">
        {user.userType === "LABOUR" && (
          <>
            <button
              onClick={() => navigate("/labour/jobs")}
              className="bg-blue-600 text-white p-4 rounded"
            >
              Nearby Jobs
            </button>

            <button
              onClick={() => navigate("/labour/update-location")}
              className="bg-indigo-600 text-white p-4 rounded"
            >
              Update Live Location
            </button>
          </>
        )}

        {user.userType === "CUSTOMER" && (
          <>
            <button
              onClick={() => navigate("/customer/post-job")}
              className="bg-green-600 text-white p-4 rounded"
            >
              Post New Job
            </button>

            <button
              onClick={() => navigate("/customer/my-jobs")}
              className="bg-emerald-600 text-white p-4 rounded"
            >
              My Jobs
            </button>
          </>
        )}
      </div>
    </div>
  );
}

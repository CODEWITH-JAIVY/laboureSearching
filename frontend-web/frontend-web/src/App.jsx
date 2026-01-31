import { useEffect } from "react";
import { Routes, Route } from "react-router-dom";

import Layout from "./layout/Layout";
import Home from "./pages/Home";
import Profile from "./pages/Profile";
import FindJobs from "./pages/FindJobs";
import HireWorkers from "./pages/HireWorkers";
import JobDetail from "./pages/JobDetail";
import OAuthSuccess from "./pages/auth/OAuthSuccess";
import Signup from "./pages/auth/Signup";
import Login from "./pages/auth/Login";
import SelectRole from "./pages/auth/SelectRole";
import LabourCreateProfile from "./pages/labour/CreateProfile";
import CustomerCreateProfile from "./pages/customer/CreateProfile";
import Dashboard from "./pages/Dashboard/Dashboard";

export default function App() {
  useEffect(() => {
    const savedTheme = localStorage.getItem("theme");
    if (savedTheme === "dark") {
      document.documentElement.classList.add("dark");
    }
  }, []);

  return (
    <div className="min-h-screen bg-gray-50 dark:bg-gray-950">
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />

          {/* AUTH */}
          <Route path="/login" element={<Login />} />
          <Route path="/signup" element={<Signup />} />
          <Route path="/oauth-success" element={<OAuthSuccess />} />
          <Route path="/select-role" element={<SelectRole />} />

          {/* PROFILE CREATION */}
          <Route
            path="/labour/create-profile"
            element={<LabourCreateProfile />}
          />
          <Route
            path="/customer/create-profile"
            element={<CustomerCreateProfile />}
          />

          {/* DASHBOARDS (ROLE BASED) */}
          <Route path="/labour/dashboard" element={<Dashboard />} />
          <Route path="/customer/dashboard" element={<Dashboard />} />

          {/* OTHER */}
          <Route path="/jobs" element={<FindJobs />} />
          <Route path="/jobs/:id" element={<JobDetail />} />
          <Route path="/hire" element={<HireWorkers />} />
          <Route path="/profile" element={<Profile />} />
        </Route>
      </Routes>
    </div>
  );
}

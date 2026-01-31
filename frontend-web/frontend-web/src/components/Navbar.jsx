import { useState, useContext } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { LanguageContext } from "../context/LanguageContext";
import useAuth from "../hooks/useAuth";




export default function Navbar() {
  const { user, role, isLoggedIn, logout } = useAuth();
  const { lang, setLang, t } = useContext(LanguageContext);
   
  const navigate = useNavigate();
  const location = useLocation();
  const [open, setOpen] = useState(false);

  const go = (path) => {
    navigate(path);
    setOpen(false);
  };

  // 🌙 Dark mode
  const toggleDarkMode = () => {
    const isDark = document.documentElement.classList.contains("dark");
    document.documentElement.classList.toggle("dark");
    localStorage.setItem("theme", isDark ? "light" : "dark");
  };

  const isDark = document.documentElement.classList.contains("dark");
  const isProfilePage = location.pathname === "/profile";

  const activeClass = (path) =>
    location.pathname === path
      ? "text-blue-600 font-semibold"
      : "hover:text-blue-600";

  return (
    <>
      {/* ================= NAVBAR ================= */}
      <nav className="bg-white dark:bg-gray-900 shadow-sm sticky top-0 z-50">
        <div className="max-w-7xl mx-auto px-4 py-4 flex justify-between items-center">
          {/* LOGO */}
          <div
            onClick={() => go("/")}
            className="cursor-pointer flex items-center gap-2"
          >
            <div className="w-9 h-9 rounded-full bg-blue-600 text-white flex items-center justify-center font-bold">
              K
            </div>
            <span className="text-xl font-bold">
              <span className="text-blue-600">Kaam</span>
              <span className="text-orange-500">Setu</span>
            </span>
          </div>

          {/* ================= DESKTOP MENU ================= */}
          <div className="hidden md:flex gap-6 items-center text-sm font-medium text-gray-700 dark:text-gray-200">
            <span
              onClick={() => go("/")}
              className={`cursor-pointer ${activeClass("/")}`}
            >
              {t("home")}
            </span>

            {/* LABOUR MENU */}
            {role === "LABOUR" && (
              <span
                onClick={() => go("/jobs")}
                className={`cursor-pointer ${activeClass("/jobs")}`}
              >
                {t("findJobs")}
              </span>
            )}

            {/* CUSTOMER MENU */}
            {role === "CUSTOMER" && (
              <span
                onClick={() => go("/hire")}
                className={`cursor-pointer ${activeClass("/hire")}`}
              >
                {t("hireWorkers")}
              </span>
            )}

            {/* PROFILE BUTTON */}
            {isLoggedIn && !isProfilePage && (
              <button
                onClick={() => go("/profile")}
                className="flex items-center gap-2 px-3 py-1 rounded-lg border hover:bg-blue-50 dark:hover:bg-gray-800"
              >
                <div className="w-7 h-7 rounded-full bg-blue-600 text-white flex items-center justify-center font-bold">
                  {(user.name || user.email || "U")[0].toUpperCase()}
                </div>
                <span>Profile</span>
              </button>
            )}
            {/* INSTAGRAM STYLE PROFILE */}
            {isLoggedIn && (
              <div
                onClick={() => go("/profile")}
                className="flex items-center gap-2 cursor-pointer hover:bg-gray-100 dark:hover:bg-gray-800 px-3 py-1 rounded-full"
              >
                <img
                  src={
                    user.profileImageUrl ||
                    "https://ui-avatars.com/api/?name=" + user.name
                  }
                  alt="profile"
                  className="w-8 h-8 rounded-full object-cover"
                />
                <span className="hidden lg:inline text-sm font-medium">
                  {user.name}
                </span>
              </div>
            )}

            {/* LANGUAGE */}
            <select
              value={lang}
              onChange={(e) => setLang(e.target.value)}
              className="border rounded-lg px-2 py-1 bg-white dark:bg-gray-800 dark:text-white"
            >
              <option value="hi">हिंदी</option>
              <option value="en">English</option>
            </select>

            {/* DARK MODE */}
            <button onClick={toggleDarkMode} className="text-xl">
              {isDark ? "☀️" : "🌙"}
            </button>

            {/* AUTH */}
            {isLoggedIn ? (
              <button
                onClick={logout}
                className="bg-red-500 text-white px-4 py-2 rounded-lg"
              >
                {t("logout")}
              </button>
            ) : (
              <>
                <button
                  onClick={() => go("/login")}
                  className="px-4 py-2 border rounded-lg"
                >
                  {t("login")}
                </button>
                <button
                  onClick={() => go("/signup")}
                  className="bg-green-600 text-white px-4 py-2 rounded-lg"
                >
                  Signup
                </button>
              </>
            )}
          </div>

          {/* MOBILE BUTTON */}
          <button
            onClick={() => setOpen(true)}
            className="md:hidden text-2xl dark:text-white"
          >
            ☰
          </button>
        </div>
      </nav>

      {/* OVERLAY */}
      {open && (
        <div
          className="fixed inset-0 bg-black/40 z-40"
          onClick={() => setOpen(false)}
        />
      )}

      {/* ================= MOBILE DRAWER ================= */}
      <div
        className={`fixed top-0 right-0 h-full w-64 bg-white dark:bg-gray-900 z-50 shadow-lg transform transition-transform duration-300 ${
          open ? "translate-x-0" : "translate-x-full"
        }`}
      >
        <div className="p-5 border-b dark:border-gray-700 flex justify-between">
          <span className="font-bold text-lg">Menu</span>
          <button onClick={() => setOpen(false)}>✕</button>
        </div>

        <div className="flex flex-col gap-5 p-5 text-sm">
          <span onClick={() => go("/")}>🏠 {t("home")}</span>

          {role === "LABOUR" && (
            <span onClick={() => go("/jobs")}>🔍 {t("findJobs")}</span>
          )}

          {role === "CUSTOMER" && (
            <span onClick={() => go("/hire")}>👷 {t("hireWorkers")}</span>
          )}

          {!isLoggedIn && (
            <>
              <span onClick={() => go("/login")}>🔑 Login</span>
              <span onClick={() => go("/signup")}>📝 Signup</span>
            </>
          )}

          {isLoggedIn && (
            <>
              {!isProfilePage && (
                <span onClick={() => go("/profile")}>👤 Profile</span>
              )}
              <button
                onClick={logout}
                className="bg-red-500 text-white py-2 rounded-lg"
              >
                {t("logout")}
              </button>
            </>
          )}
        </div>
      </div>
    </>
  );
}

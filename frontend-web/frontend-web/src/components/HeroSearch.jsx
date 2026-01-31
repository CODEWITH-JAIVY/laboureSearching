import { useState, useContext } from "react";
import { LanguageContext } from "../context/LanguageContext";
import { searchJobsService } from "../services/search.service";

export default function HeroSearch() {
  const { t } = useContext(LanguageContext);

  const [keyword, setKeyword] = useState("");
  const [location, setLocation] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSearch = async () => {
    if (!keyword || !location) return;

    try {
      setLoading(true);

      // 🔥 Service call (NOT api / axios)
      const jobs = await searchJobsService({
        keyword,
        location,
      });

      console.log("SEARCH RESULT 👉", jobs);
      // 👉 later: navigate("/jobs", { state: jobs })
    } catch (err) {
      console.error("Search failed", err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <section
      className="
        bg-gradient-to-r 
        from-blue-600 
        to-blue-500 
        dark:from-blue-900 
        dark:to-blue-950 
        py-16 
        sm:py-20
      "
    >
      <div className="w-full max-w-6xl mx-auto px-4 sm:px-6">
        {/* BRAND */}
        <h1 className="text-3xl sm:text-4xl font-extrabold text-white">
          Kaam<span className="text-orange-300">Setu</span>
        </h1>

        {/* TAGLINE */}
        <p className="mt-3 text-lg sm:text-xl text-blue-100">
          {t("heroTagline")}
        </p>

        {/* SEARCH CARD */}
        <div
          className="
            mt-8 
            bg-white 
            dark:bg-gray-900 
            rounded-2xl 
            p-4 
            shadow-lg
            grid 
            grid-cols-1 
            md:grid-cols-3 
            gap-3
          "
        >
          <input
            className="
              w-full px-4 py-3 border rounded-xl outline-none
              bg-white dark:bg-gray-800
              text-gray-700 dark:text-gray-100
              border-gray-300 dark:border-gray-700
            "
            placeholder={t("searchPlaceholder")}
            value={keyword}
            onChange={(e) => setKeyword(e.target.value)}
          />

          <input
            className="
              w-full px-4 py-3 border rounded-xl outline-none
              bg-white dark:bg-gray-800
              text-gray-700 dark:text-gray-100
              border-gray-300 dark:border-gray-700
            "
            placeholder={t("locationPlaceholder")}
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          />

          <button
            onClick={handleSearch}
            disabled={loading}
            className="
              w-full bg-green-600 hover:bg-green-700
              dark:bg-green-700 dark:hover:bg-green-600
              text-white py-3 rounded-xl font-semibold
            "
          >
            {loading ? t("searching") : t("searchBtn")}
          </button>
        </div>
      </div>
    </section>
  );
}

import { useContext } from "react";
import { LanguageContext } from "../context/LanguageContext";

export default function SearchBar() {
  const { t } = useContext(LanguageContext);

  return (
    <div className="px-4 mt-4">
      <div className="bg-white rounded-2xl shadow-md p-4 space-y-3">
        
        {/* Search Input */}
        <div className="flex items-center gap-2 border rounded-xl px-3 py-2">
          <span>🔍</span>
          <input
            type="text"
            placeholder={t("searchPlaceholder")}
            className="w-full outline-none text-sm"
          />
        </div>

        {/* Location */}
        <div className="flex items-center gap-2 text-sm text-gray-600">
          <span>📍</span>
          <span>{t("location")}</span>
        </div>

      </div>
    </div>
  );
}

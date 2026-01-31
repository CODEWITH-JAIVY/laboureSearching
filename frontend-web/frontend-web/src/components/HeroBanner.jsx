import { useContext } from "react";
import { LanguageContext } from "../context/LanguageContext";

export default function HeroBanner() {
  const { t } = useContext(LanguageContext);

  return (
    <section className="px-4 sm:px-6 mt-6">
      <div className="max-w-6xl mx-auto bg-gradient-to-r from-blue-600 to-blue-500 rounded-2xl p-6 sm:p-10 text-white shadow-lg">
        <h2 className="text-xl sm:text-3xl font-bold">{t("heroBrand")}</h2>

        <p className="mt-2 text-sm sm:text-base text-blue-100">
          {t("heroTagline")}
        </p>

        <div className="mt-6">
          <button className="bg-white text-blue-600 px-6 py-3 rounded-xl text-sm font-semibold hover:bg-blue-50">
            {t("start")}
          </button>
        </div>
      </div>
    </section>
  );
}

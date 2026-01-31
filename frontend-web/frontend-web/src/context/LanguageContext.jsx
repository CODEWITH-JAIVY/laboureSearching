import { createContext, useEffect, useState } from "react";

export const LanguageContext = createContext();

const translations = {
  hi: {
    home: "होम",
    findJobs: "नौकरी खोजें",
    hireWorkers: "कामगार रखें",
    profile: "प्रोफाइल",
    login: "लॉगिन",
    logout: "लॉगआउट",

    heroBrand: "KaamSetu",
    heroTagline: "स्थानीय नौकरी और मज़दूर मंच",
    searchPlaceholder: "नौकरी या काम खोजें",
    locationPlaceholder: "स्थान दर्ज करें",
    searchBtn: "खोजें",
    searching: "खोजा जा रहा है...",
    start: "शुरू करें",
  },

  en: {
    home: "Home",
    findJobs: "Find Jobs",
    hireWorkers: "Hire Workers",
    profile: "Profile",
    login: "Login",
    logout: "Logout",

    heroBrand: "KaamSetu",
    heroTagline: "Local Job & Labour Platform",
    searchPlaceholder: "Search jobs or workers",
    locationPlaceholder: "Enter location",
    searchBtn: "Search",
    searching: "Searching...",
    start: "Get Started",
  },
};

export function LanguageProvider({ children }) {
  const [lang, setLang] = useState("hi");

  // Load language
  useEffect(() => {
    const savedLang = localStorage.getItem("lang");
    if (savedLang) setLang(savedLang);
  }, []);

  // Save language
  useEffect(() => {
    localStorage.setItem("lang", lang);
  }, [lang]);

  const t = (key) => translations[lang][key] || key;

  return (
    <LanguageContext.Provider value={{ lang, setLang, t }}>
      {children}
    </LanguageContext.Provider>
  );
}

import axios from "axios";

const instance = axios.create({
  baseURL: "http://localhost:8080", // 🔥 Spring Boot backend
  headers: {
    "Content-Type": "application/json",
  },
});

// 🔐 Request interceptor (JWT auto attach)
instance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

instance.interceptors.response.use(
  (response) => response,
  (error) => Promise.reject(error)
);

export default instance;

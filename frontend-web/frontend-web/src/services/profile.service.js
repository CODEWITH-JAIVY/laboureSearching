import api from "../api/axios";
// api = axios instance with baseURL + auth token

// ================= PROFILE =================
export const getProfileService = async () => {
  const res = await api.get("/user/profile/me");
  return res.data;
};

export const updateProfileService = async (data) => {
  const res = await api.put("/user/profile/update", data);
  return res.data;
};

// ================= PROFILE IMAGE =================
export const uploadProfileImageService = async (file, onProgress) => {
  const formData = new FormData();
  formData.append("file", file);

  const res = await api.post("/user/profile/image", formData, {
    headers: { "Content-Type": "multipart/form-data" },
    onUploadProgress: (e) => {
      if (onProgress) {
        const percent = Math.round((e.loaded * 100) / e.total);
        onProgress(percent);
      }
    },
  });

  return res.data; // image URL
};

// ================= ADDRESS =================
export const saveAddressService = async (address) => {
  const res = await api.post("/user/profile/address", address);
  return res.data;
};

// ================= LIVE LOCATION =================
export const saveLiveLocationService = async ({ lat, lng }) => {
  const res = await api.post("/user/profile/location", {
    latitude: lat,
    longitude: lng,
  });
  return res.data;
};

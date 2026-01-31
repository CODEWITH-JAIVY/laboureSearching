import api from "./axios";

export const getProfileApi = () => {
  return api.get("/user/profile/me");
};

export const updateProfileApi = (data) => {
  return api.put("/user/profile/me", data);
};

export const uploadProfileImageApi = (file, onProgress) => {
  const formData = new FormData();
  formData.append("file", file);

  return api.post("/user/me/profile/photo", formData, {
    onUploadProgress: (progressEvent) => {
      if (!progressEvent.total) return;
      const percent = Math.round(
        (progressEvent.loaded * 100) / progressEvent.total
      );
      onProgress(percent);
    },
  });
}
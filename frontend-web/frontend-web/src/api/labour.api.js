import api from "./axios";

export const getWorkImagesApi = () =>
  api.get("/labour/work-images");

export const uploadWorkImageApi = (file) => {
  const form = new FormData();
  form.append("file", file);
  return api.post("/labour/work-images", form);
};

export const deleteWorkImageApi = (id) =>
  api.delete(`/labour/work-images/${id}`);

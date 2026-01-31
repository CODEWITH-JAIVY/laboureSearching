import {
  getWorkImagesApi,
  uploadWorkImageApi,
  deleteWorkImageApi,
} from "../api/labour.api";

export const getWorkImagesService = async () => {
  const res = await getWorkImagesApi();
  return res.data;
};

export const uploadWorkImageService = async (file) => {
  await uploadWorkImageApi(file);
};

export const deleteWorkImageService = async (id) => {
  await deleteWorkImageApi(id);
};

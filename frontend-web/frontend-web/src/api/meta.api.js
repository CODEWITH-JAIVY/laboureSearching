import axios from "../utils/axios";

/**
 * 🔹 Fetch all labour types from backend enum
 */
export const getLabourTypesApi = async () => {
  const res = await axios.get("/meta/labour-types");
  return res.data; // ["MASON", "PAINTER", ...]
};

/**
 * 🔹 Fetch all gender types from backend enum
 */
export const getGenderTypesApi = async () => {
  const res = await axios.get("/meta/gender-types");
  return res.data; // ["MALE", "FEMALE", "ANY"]
};

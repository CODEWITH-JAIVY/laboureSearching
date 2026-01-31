import api from "./axios";

/**
 * 📄 Get job detail by ID
 * Backend: GET /jobs/{id}
 */
export const getJobById = (id) => {
  return api.get(`/jobs/${id}`);
};
 
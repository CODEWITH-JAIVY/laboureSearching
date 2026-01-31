import api from "./axios";

/**
 * 🔍 JOB / WORKER SEARCH API
 */
export const searchJobsOrWorkersApi = (payload) => {
  return api.post("/search", payload);
};

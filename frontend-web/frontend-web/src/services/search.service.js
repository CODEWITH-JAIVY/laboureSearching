import { searchJobsOrWorkersApi } from "../api/search.api";

/**
 * 🔍 SEARCH SERVICE
 * (business logic future में यहीं आएगा)
 */
export const searchJobsService = async (payload) => {
  const res = await searchJobsOrWorkersApi(payload);
  return res.data;
};

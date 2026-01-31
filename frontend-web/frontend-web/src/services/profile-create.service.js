import api from "../api/axios";

export const createLabourProfileService = () => {
  return api.patch("/laboure/createProfile", {
    skill: "Helper",
    experience: 1,
  });
};

export const createCustomerProfileService = () => {
  return api.post("/customer/create-profile", {
    companyName: "Local Customer",
  });
};

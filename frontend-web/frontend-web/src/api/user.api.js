import api from "../utils/axios";

export const setUserTypeApi = (userType) => {
  // console.console.log("in user api " , userType);

  return api.post("/user/select-role", {
    userType, // "LABOUR" | "CUSTOMER"
  });
};

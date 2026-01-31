import api from "./axios";

/**
 * 🔐 EMAIL SIGNUP / AUTO LOGIN
 * Backend: POST /user/sing/email
 */
export const signupWithEmail = (payload) => {
  return api.post("/user/sing/email", payload);
};

/**
 * 🔐 EMAIL LOGIN
 * Backend: POST /user/login
 */
export const loginWithEmail = (payload) => {
  return api.post("/user/login", payload);
};

/**
 * 👤 GET MY PROFILE (after login)
 * Backend: GET /user/profile/me    
 * when the user login hen this will be  called automatic to show there profile 
 */
export const getMyProfile = () => {
  return api.get("/user/profile/me");
};

/**
 * 🔑 GOOGLE LOGIN (OAuth2 redirect)
 * Backend: /oauth2/authorization/google
 * (no axios needed)
 */
export const loginWithGoogle = () => {
  window.location.href =
    "http://localhost:8080/oauth2/authorization/google";
};   
  
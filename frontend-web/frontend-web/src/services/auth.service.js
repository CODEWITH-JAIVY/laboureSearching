// src/services/auth.service.js

import {
  loginWithEmail,
  signupWithEmail,
  getMyProfile,
} from "../api/auth.api";

/**
 * 🔐 EMAIL LOGIN SERVICE
 */
export const loginUserService = async (payload) => {
  const res = await loginWithEmail(payload);

  const { profileExists } = res.data;

  return {
    user: res.data,
    redirectTo: profileExists ? "/profile" : "/create-profile",
  };
};

/**
 * 🔐 EMAIL SIGNUP SERVICE
 */
export const signupUserService = async (payload) => {
  const res = await signupWithEmail(payload);

  // signup ke baad profile nahi hota
  return {
    user: res.data,
    redirectTo: "/create-profile",
  };
};

/**
 * 🔑 GOOGLE OAUTH SUCCESS SERVICE
 */
export const googleLoginService = async () => {
  const res = await getMyProfile();

  const { profileExists } = res.data;

  return {
    user: res.data,
    redirectTo: profileExists ? "/profile" : "/create-profile",
  };
};

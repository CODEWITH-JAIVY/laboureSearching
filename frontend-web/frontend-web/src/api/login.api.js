import api from "./axios";

/**
 * 📲 SEND OTP FOR LOGIN
 * POST /auth/login/otp/send
 */
export const sendLoginOtp = (mobile) => {
  return api.post("/auth/login/otp/send", { mobile });
};

/**
 * ✅ VERIFY OTP + LOGIN
 * POST /auth/login/otp/verify
 */
export const verifyLoginOtp = (mobile, otp) => {
  return api.post("/auth/login/otp/verify", { mobile, otp });
};

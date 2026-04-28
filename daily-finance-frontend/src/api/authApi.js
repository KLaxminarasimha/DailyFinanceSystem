import axios from "axios";

const API = "http://localhost:9999/api/v1";

export const customerRegister = (data) => {
  return axios.post(`${API}/customer/register`, data);
};

export const verifyOtp = (data) => {
  return axios.post(`${API}/auth/verify-otp`, data);
};

export const loginUser = (data) => {
  return axios.post(`${API}/auth/login`, data);
};
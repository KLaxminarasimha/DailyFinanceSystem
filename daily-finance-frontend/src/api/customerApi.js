import axios from "axios";

const API = "http://localhost:9999/customers";

export const createCustomer = (data, userId) => {
  return axios.post(`${API}/profile`, data, {
    headers: {
      "X-USER-ID": userId,
    },
  });
};
import axios from "axios";

const storedBasicAuth = sessionStorage.getItem("basicAuth");

const API = axios.create({
  baseURL: "http://localhost:8081/",
  headers: storedBasicAuth
    ? {
        Authorization: `Basic ${storedBasicAuth}`,
      }
    : {},
});

export const setBasicAuth = (email, password) => {
  const token = btoa(`${email}:${password}`);
  sessionStorage.setItem("basicAuth", token);
  API.defaults.headers.common.Authorization = `Basic ${token}`;
};

export const clearBasicAuth = () => {
  sessionStorage.removeItem("basicAuth");
  delete API.defaults.headers.common.Authorization;
};

export default API;

import { createContext, useContext, useState } from "react";
import API from "../axios";

export const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [token, setToken] = useState(() => localStorage.getItem("token"));
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [currentUser, setCurrentUser] = useState(() =>
    Boolean(localStorage.getItem("token"))
  );

  const login = async (email, password) => {
    setLoading(true);
    setError("");

    try {
      const response = await API.post("/public/login", { email, password });
      const authToken = response.data;

      localStorage.setItem("token", authToken);
      setToken(authToken);
      setCurrentUser(true);

      return {
        success: true,
        token: authToken,
      };
    } catch (err) {
      const message = err.response?.data || err.message || "Login failed";
      setError(message);

      return {
        success: false,
        message,
      };
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    localStorage.removeItem("token");
    setToken(null);
    setCurrentUser(false);
  };

  return (
    <AuthContext.Provider
      value={{
        currentUser,
        loading,
        error,
        token,
        login,
        logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error("useAuth must be used within an AuthProvider");
  }

  return context;
};

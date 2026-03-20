import React, { useState } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "./context/AuthContext";

function Login() {
  const { login, error, loading } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [user, setUser] = useState({
    email: "",
    password: "",
  });

  const handleLogin = async () => {
    const res = await login(user.email, user.password);

    if (res.success) {
      const redirectTo = location.state?.from?.pathname || "/UserProfiles";
      navigate(redirectTo, { replace: true });
      alert("Login Successful");
      return;
    }
    alert(res.message || "Login Failed");
  };

  return (
    <div>
      <h2>Login</h2>

      <input
        placeholder="Email"
        onChange={(e) => setUser({ ...user, email: e.target.value })}
      />

      <input
        type="password"
        placeholder="Password"
        onChange={(e) => setUser({ ...user, password: e.target.value })}
      />

      <button onClick={handleLogin} disabled={loading}>
        {loading ? "Logging in..." : "Login"}
      </button>

      {error ?
        <p>{error}</p>
      : null}

      <p>
        New here? <Link to="/signup">Create an account</Link>
      </p>
    </div>
  );
}

export default Login;

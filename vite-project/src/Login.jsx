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

  const handleGoogleLogin = () => {
    const clientId = "890684994042-3r6b8vsgag5j8m33dirvcm7l6cagvu6e.apps.googleusercontent.com";

    const redirectUri = "http://localhost:8081/auth/callback";

    const scope = "openid email profile";

    const url = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code&scope=${scope}&access_type=offline`;

    window.location.href = url;
  };
  
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
      <button onClick={handleGoogleLogin}>Login with Google</button>
    </div>
  );
}

export default Login;

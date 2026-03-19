import React, { useState } from "react";
import API, { setBasicAuth } from "./axios";

function Login() {
  const [user, setUser] = useState({
    email: "",
    password: "",
  });

  const handleLogin = async () => {
    try {
      const res = await API.post("/public/login", user);

      if (res.data) {
        setBasicAuth(user.email, user.password);
        alert("Login Successful");
        console.log(res.data);
      } else {
        alert("Invalid Credentials");
      }
    } catch (err) {
      alert("Login Failed");
    }
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

      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

export default Login;

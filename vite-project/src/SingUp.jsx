import React, { useState } from "react";
import API from "./axios";

function SingUp() {
  const [user, setUser] = useState({
    name: "",
    email: "",
    password: "",
  });

  const handleSignup = async () => {
    try {
      const res = await API.post("/signup", user);
      alert("Signup Successful");
      console.log(res.data);
    } catch (err) {
      alert("Error in signup");
    }
  };

  return (
    <div>
      <h2>Signup</h2>

      <input
        placeholder="Name"
        onChange={(e) => setUser({ ...user, name: e.target.value })}
      />

      <input
        placeholder="Email"
        onChange={(e) => setUser({ ...user, email: e.target.value })}
      />

      <input
        type="password"
        placeholder="Password"
        onChange={(e) => setUser({ ...user, password: e.target.value })}
      />

      <button onClick={handleSignup}>Signup</button>
    </div>
  );
}

export default SingUp;

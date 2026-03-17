import { useState } from "react";
import "./App.css";
import SingUp from "./SingUp";
import Login from "./Login";

function App() {
  const [count, setCount] = useState(0);

  return (
    <>
      <SingUp />
      <Login />
    </>
  );
}

export default App;
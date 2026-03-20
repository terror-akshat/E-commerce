import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./Login";
import { ProtectedRoute } from "./ProtectedRoute";
import SingUp from "./SingUp";
import UserProfiles from "./UserProfiles";
import { AuthProvider } from "./context/AuthContext";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/signup" element={<SingUp />} />
          <Route element={<ProtectedRoute />}>
            <Route path="/UserProfiles" element={<UserProfiles />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;

import "./App.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import Login from "./Login";
import { ProtectedRoute } from "./ProtectedRoute";
import SingUp from "./SingUp";
import UserProfiles from "./UserProfiles";
import { AuthProvider } from "./context/AuthContext";
import OAuthSuccess from "./context/OAuthSuccess";

function App() {
  return (
    <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/signup" element={<SingUp />} />
          <Route path="/oauth-success" element={<OAuthSuccess />} />
          <Route element={<ProtectedRoute />}>
            <Route path="/UserProfiles" element={<UserProfiles />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </AuthProvider>
  );
}

export default App;
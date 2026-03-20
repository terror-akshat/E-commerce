import { useNavigate } from "react-router-dom";
import { useAuth } from "./context/AuthContext";

function UserProfiles() {
  const { logout } = useAuth();
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate("/", { replace: true });
  };

  return (
    <div>
      <h2>User Profile</h2>
      <p>You are logged in.</p>
      <button onClick={handleLogout}>Logout</button>
    </div>
  );
}

export default UserProfiles;
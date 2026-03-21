import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "./AuthContext";

const OAuthSuccess = () => {
  const navigate = useNavigate();
  const { loginWithGoogleCode } = useAuth();

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);

    const token = params.get("token");
    const error = params.get("error");

    if (error) {
      alert("Google login failed");
      navigate("/", { replace: true });
      return;
    }

    const res = loginWithGoogleCode(token);
    if (res.success) {
      navigate("/userProfiles", { replace: true });
    }
  }, [navigate]);

  return <h2>Logging you in...</h2>;
};

export default OAuthSuccess;
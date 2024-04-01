import {useNavigate} from "react-router-dom";
import {authService} from "../../services/authService";

const Header = () => {
    const navigate = useNavigate();
    const accessToken = authService.getAccessToken();

    const handleCreateWorksheet = () => {
        navigate("createOrUpdateWorksheet");
    };
    const getAllWorksheets = () => {
        navigate("worksheets");
    };
    const handleLogout = () => {
        authService.deleteTokens();
        navigate("login");
    };
    const handleRegister = () => {
        navigate("register");
    };


    return (
        <div>
            {accessToken && (
                <>
                    <button onClick={handleCreateWorksheet}>Create new worksheet</button>
                    <button onClick={getAllWorksheets}>Get all worksheets</button>
                </>
            )}
            <button onClick={accessToken ? handleLogout : () => navigate("login")}>
                {accessToken ? "Logout" : "Login"}
            </button>

            {!accessToken && (
                <button onClick={handleRegister}>Register</button>
            )}
        </div>
    );
};

export {Header};
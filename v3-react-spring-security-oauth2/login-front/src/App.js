import "./styles.css";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import { useEffect, useState } from "react";

import Login from "./Login";
import Home from "./Home";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import Logout from "./Logout";
import axios from "axios";
import MyPage from "./MyPage";

axios.defaults.baseURL = "http://localhost:8080";
axios.defaults.withCredentials = true;

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  const checkAuthStatus = async () => {
    return await axios.get("/auth");
  };

  useEffect(() => {
    checkAuthStatus()
      .then((response) => {
        setIsAuthenticated(response.data.isAuthenticated);
        console.log(response.data.isAuthenticated);
      })
      .catch((error) => {
        setIsAuthenticated(false);
        console.error("Session check failed", error);
      });
  }, []);

  return (
    <div>
      <ToastContainer
        position="top-center"
        autoClose={1000}
        draggable
        pauseOnHover
        limit={1}
      />
      <BrowserRouter>
        <Routes>
          <Route
            path="/"
            element={<Home isAuthenticated={isAuthenticated} />}
          />
          <Route path="/login" element={<Login />} />
          <Route path="/logout" element={<Logout />} />
          <Route path="/my-page" element={<MyPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;

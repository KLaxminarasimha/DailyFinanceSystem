import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import CustomerRegister from "./pages/CustomerRegister";
import VerifyOtp from "./pages/VerifyOtp";
import Login from "./pages/Login";
import "./index.css";

function App() {
  return (
    <BrowserRouter>
      <div className="app">
        <div className="card">
          <h1>Daily Finance</h1>

          <nav>
            <Link to="/register">Register</Link>
            <Link to="/verify-otp">Verify OTP</Link>
            <Link to="/login">Login</Link>
          </nav>

          <Routes>
            <Route path="/" element={<CustomerRegister />} />
            <Route path="/register" element={<CustomerRegister />} />
            <Route path="/verify-otp" element={<VerifyOtp />} />
            <Route path="/login" element={<Login />} />
          </Routes>
        </div>
      </div>
    </BrowserRouter>
  );
}

export default App;
import React, { useState } from "react";
import { verifyOtp } from "../api/authApi";

function VerifyOtp() {
  const [otp, setOtp] = useState("");
  const email = localStorage.getItem("email");

  const submit = async (e) => {
    e.preventDefault();

    try {
      const response = await verifyOtp({
        email: email,
        otp: otp,
      });

      console.log(response.data);
      alert("OTP verified successfully");
    } catch (error) {
      console.log(error);
      alert(error.response?.data?.message || "OTP verification failed");
    }
  };

  return (
    <div>
      <h2>Verify OTP</h2>

      <form onSubmit={submit}>
        <input value={email || ""} readOnly />

        <input
          placeholder="Enter OTP"
          value={otp}
          onChange={(e) => setOtp(e.target.value)}
          required
        />

        <button type="submit">Verify OTP</button>
      </form>
    </div>
  );
}

export default VerifyOtp;
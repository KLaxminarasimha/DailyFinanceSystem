import React, { useState } from "react";
import { customerRegister } from "../api/authApi";

function CustomerRegister() {
  const [form, setForm] = useState({
    name: "",
    email: "",
    phone: "",
    password: "",
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const submit = async (e) => {
    e.preventDefault();

    try {
      const response = await customerRegister(form);

      console.log(response.data);

      const userId = response.data.data.userId;
      localStorage.setItem("userId", userId);
      localStorage.setItem("email", form.email);

      alert("Registered successfully. Please verify OTP.");
    } catch (error) {
      console.log(error);
      alert(error.response?.data?.message || "Registration failed");
    }
  };

  return (
    <div>
      <h2>Customer Register</h2>

      <form onSubmit={submit}>
        <input
          name="name"
          placeholder="Full Name"
          value={form.name}
          onChange={handleChange}
          required
        />

        <input
          type="email"
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
          required
        />

        <input
          name="phone"
          placeholder="Phone"
          value={form.phone}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="password"
          placeholder="Password"
          value={form.password}
          onChange={handleChange}
          required
        />

        <button type="submit">Register</button>
      </form>
    </div>
  );
}

export default CustomerRegister;
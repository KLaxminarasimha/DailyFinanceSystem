import React, { useState } from "react";
import { createCustomer } from "../api/customerApi";

function CreateCustomer() {
  const [userId, setUserId] = useState("");

  const [form, setForm] = useState({
    firstName: "",
    lastName: "",
    email: "",
    phone: "",
    dob: "",
    gender: "",
    address: "",
    city: "",
    state: "",
    pincode: "",
    userType: "EMPLOYEE",
  });

  const handleChange = (e) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const submit = async (e) => {
  e.preventDefault();

  try {
    const response = await createCustomer(form, userId);
    console.log(response.data);
    alert("Customer Created Successfully");
  } catch (error) {
    console.log("Full error:", error);
    console.log("Response:", error.response);
    console.log("Error data:", error.response?.data);

    alert(
      error.response?.data?.message ||
      error.response?.data ||
      error.message ||
      "Error creating customer"
    );
  }
};

  return (
    <div>
      <h2>Create Customer</h2>

      <form onSubmit={submit}>
        <input
          type="number"
          placeholder="Auth User ID"
          value={userId}
          onChange={(e) => setUserId(e.target.value)}
          required
        />

        <input
          name="firstName"
          placeholder="First Name"
          value={form.firstName}
          onChange={handleChange}
          required
        />

        <input
          name="lastName"
          placeholder="Last Name"
          value={form.lastName}
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
          type="date"
          name="dob"
          value={form.dob}
          onChange={handleChange}
          required
        />

        <select
          name="gender"
          value={form.gender}
          onChange={handleChange}
          required
        >
          <option value="">Select Gender</option>
          <option value="MALE">Male</option>
          <option value="FEMALE">Female</option>
        </select>

        <input
          name="address"
          placeholder="Address"
          value={form.address}
          onChange={handleChange}
          required
        />

        <input
          name="city"
          placeholder="City"
          value={form.city}
          onChange={handleChange}
          required
        />

        <input
          name="state"
          placeholder="State"
          value={form.state}
          onChange={handleChange}
          required
        />

        <input
          name="pincode"
          placeholder="Pincode"
          value={form.pincode}
          onChange={handleChange}
          required
        />

        <select
          name="userType"
          value={form.userType}
          onChange={handleChange}
        >
          <option value="EMPLOYEE">Employee</option>
          <option value="BUSINESS">Business</option>
        </select>

        <button type="submit">Create Customer</button>
      </form>
    </div>
  );
}

export default CreateCustomer;
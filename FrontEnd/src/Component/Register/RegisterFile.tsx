import React, { useState } from 'react';
import './RegisterForm.css';

function RegisterFile() {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    username: '',
    password: ''
  });

  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };


  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault(); 


    if (!formData.firstName || !formData.lastName || !formData.username || formData.password.length < 4) {
      setError( 'All fields are required and password must be at least 4 characters long');
      return;
    }

    try {
      const response = await fetch('http://localhost:8080/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        setSuccess('Registration successful! You can now log in');
        setError('');
        setFormData({
          firstName: '',
          lastName: '',
          username: '',
          password: '',
        });
      } else {
        const errorMessage = await response.text();
        setError(errorMessage);
        setSuccess('');
      }
    } catch (err) {
      setError('An error occurred while registering. Please try again.');
      setSuccess('');
    }
  };

  return (
    <div className='register-container'>
      <h3>Create Your Account</h3>
      <form className='register-form' onSubmit={handleSubmit}>
        {error && <div className="error">{error}</div>}
        {success && <div className="success">{success}</div>}
        <label className='first-name'>
          First Name
          <input
            type='text'
            name='firstName'
            value={formData.firstName}
            onChange={handleChange}
          />
        </label>
        <label className='last-name'>
          Last Name
          <input
            type='text'
            name='lastName'
            value={formData.lastName}
            onChange={handleChange}
          />
        </label>
        <label className='register-username'>
          Username
          <input
            type='text'
            name='username'
            value={formData.username}
            onChange={handleChange}
          />
        </label>
        <label className='register-password'>
          Password
          <input
            type='password'
            name='password'
            value={formData.password}
            onChange={handleChange}
          />
        </label>
        <button type='submit'>Submit</button>
      </form>
    </div>
  );
}

export default RegisterFile;

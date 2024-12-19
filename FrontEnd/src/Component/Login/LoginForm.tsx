import React, { useState } from 'react';
import './LoginForm.css';
import { useNavigate } from 'react-router-dom';

function LoginForm() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e: { preventDefault: () => void; }) => {
    e.preventDefault(); 

    try {
      const response = await fetch('http://localhost:8080/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username, password }),
      });

      if (!response.ok) {
        const errorMessage = await response.text();
        setError(errorMessage || 'Invalid username or password');
        return;
      }

      const userData = await response.json(); 
      setError('');

      if (userData.role === 'Employee') {
        navigate('/employee-account');
      } else if (userData.role === 'Manager') {
        navigate('/manager-account');
      }
    } catch (err) {
      setError('An error occurred while logging in. Please try again.');
    }
  };

  return (
    <div className="login-container">
      <h3>Login to Your Account</h3>
      <form className="login-form" onSubmit={handleLogin}>
        {error && <div className="error">{error}</div>}
        <label className="username">
          Username
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            placeholder="Enter your username"
          />
        </label>
        <label className="password">
          Password
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            placeholder="Enter your password"
          />
        </label>
        <button type="submit" className="button">
          Login
        </button>
      </form>
    </div>
  );
}

export default LoginForm;

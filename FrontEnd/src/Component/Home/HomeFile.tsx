import React from 'react'
import { useNavigate } from 'react-router-dom'
import './HomeFile.css';

function HomeFile() {
  const navigate = useNavigate();
  return (
    <div className="container">
        <>
        <h2 className='content'>Welcome to The Ticketing System</h2>
        </>
      <div className="button-container">
        <button id="login-button" onClick={() => navigate('/login')}>Login to Your Account</button>
        <button id="register-button" onClick={() => navigate('/register')}>Create a New Account</button>
      </div>
    </div>
  );
}

export default HomeFile
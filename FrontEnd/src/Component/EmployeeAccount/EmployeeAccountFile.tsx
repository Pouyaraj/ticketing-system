import React from 'react'
import { useNavigate } from 'react-router-dom'
import "./EmployeeAccountFile.css";

function EmployeeAccountFile() {
  const navigate = useNavigate();
  return (
    <div className='container'>
      <div className='button-container'>
        <button id='create-button' onClick={()=>navigate('/create-ticket')}>Create a Ticket</button>
        <button id='view-button' onClick={()=>navigate('/view-ticket')}>View Previous Tickets</button>
      </div>
    </div>
  )
}

export default EmployeeAccountFile
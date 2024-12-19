import React, { useState } from 'react';
import './CreateTicketsFile.css'

function CreateTicketsFile() {
  const [amount, setAmount] = useState('');
  const [description, setDescription] = useState('');
  const [error, setError] = useState(''); 
  const [success, setSuccess] = useState('');

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    const ticketData = {
      amount: parseFloat(amount),
      description: description,
      status: "Pending",
    };

    if(!ticketData.amount || !ticketData.description){
      setError('All fields are required');
      return
    }

    try {
      const response = await fetch('http://localhost:8080/tickets/submit', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(ticketData),
      });

      if (response.ok) {
        const result = await response.json();
        setSuccess(`Ticket submitted successfully! Ticket ID: ${result.id}`);
        setError('');
      } else {
        const errorText = await response.text();
        setError(`Error: ${errorText}`);
        setSuccess(''); 
      }
    } catch (error) {
      setError('Error: Unable to submit the ticket.');
      setSuccess(''); 
    }
  };

  return (
    <div className='create-ticket-container'>
      <h2>Create a Ticket</h2>
      <form className='create-form' onSubmit={handleSubmit}>
      {success && <p id="success">{success}</p>}
      {error && <p id="error">{error}</p>}
        <div>
          <label id='amount'>Amount: </label>
          <input
            type="number" id='amount-box'
            value={amount}
            onChange={(e) => setAmount(e.target.value)}
            required
          />
        </div>
        <div>
          <label id='description'>Description: </label>
          <textarea
             id='description-box'
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            required
          />
        </div>
        <button type="submit">Submit Ticket</button>
      </form>
    </div>
  );
}

export default CreateTicketsFile;


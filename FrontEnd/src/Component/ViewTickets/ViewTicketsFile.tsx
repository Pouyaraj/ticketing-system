import React, { useEffect, useState } from 'react';
import "./ViewTicketsFile.css"
// Define the interface for the ticket data
interface Ticket {
  id: number;
  description: string;
  amount: number;
  status: string;
}

function ViewTicketsFile() {
 
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [load, setLoad] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        const response = await fetch('http://localhost:8080/tickets/get-tickets'); 
        if (!response.ok) {
          throw new Error('Failed to fetch tickets');
        }

        const data: Ticket[] = await response.json(); 
        setTickets(data);
      } catch (err) {
        setError('Failed to fetch tickets');
        console.error(err);
      } finally {
        setLoad(false); 
      }
    };

    fetchTickets();
  }, []); 

  return (
    <div>
      <h1>View Tickets</h1>

      {load}
      {error && <p>{error}</p>}

      {!error && (
        <table className='table'>
          <thead>
            <tr>
              <th>ID</th>
              <th>Description</th>
              <th>Amount</th>
              <th>Status</th>
            </tr>
          </thead>
          <tbody>
            {tickets.map((ticket) => (
              <tr key={ticket.id}>
                <td>{ticket.id}</td>
                <td>{ticket.description}</td>
                <td>{ticket.amount}</td>
                <td>{ticket.status}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
}

export default ViewTicketsFile;

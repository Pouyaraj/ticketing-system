import React, { useEffect, useState } from 'react';

// Define the interface for the ticket data
interface Ticket {
  id: number;
  description: string;
  amount: number;
  status: string;
}

function ViewTicketsFile() {
  // State to hold the list of tickets
  const [tickets, setTickets] = useState<Ticket[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchTickets = async () => {
      try {
        const response = await fetch('http://localhost:8080/tickets/get-tickets'); // Replace with your backend URL
        if (!response.ok) {
          throw new Error('Failed to fetch tickets');
        }

        const data: Ticket[] = await response.json(); // Parse the response data
        setTickets(data); // Update tickets state with the fetched data
      } catch (err) {
        setError('Failed to fetch tickets');
        console.error(err);
      } finally {
        setLoading(false); // Hide loading after fetching
      }
    };

    fetchTickets();
  }, []); // Empty dependency array to run only once when component mounts

  return (
    <div>
      <h1>View Tickets</h1>

      {loading && <p>Loading tickets...</p>}
      {error && <p>{error}</p>}

      {!loading && !error && (
        <table>
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

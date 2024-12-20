import React, { useState } from 'react';

// Define the TicketEntity type
interface TicketEntity {
  id: number;
  amount: number;
  description: string;
  status: string;
}

function ManagerAccount() {
  // Type the pendingTickets state with TicketEntity[]
  const [pendingTickets, setPendingTickets] = useState<TicketEntity[]>([]);

  // Fetch pending tickets manually
  const fetchPendingTickets = () => {
    fetch('http://localhost:8080/tickets/pending')
      .then(response => response.json())
      .then(data => {
        setPendingTickets(data);
      })
      .catch(error => {
        console.error("There was an error fetching the pending tickets:", error);
      });
  };

  // Process the ticket (approve/deny)
  const handleProcessTicket = (ticketId: number, status: string) => {
    fetch(`http://localhost:8080/tickets/process/${ticketId}`, {
      method: 'PATCH',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        status: status,
      }),
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Ticket processing failed');
        }
        return response.json();
      })
      .then(updatedTicket => {
        // Update the state by replacing the processed ticket with the updated ticket
        setPendingTickets(pendingTickets.map(ticket =>
          ticket.id === ticketId ? updatedTicket : ticket
        ));
      })
      .catch(error => {
        console.error("There was an error processing the ticket:", error);
      });
  };

  return (
    <div>
      <h2>Pending Tickets</h2>
      {}
      <button onClick={fetchPendingTickets}>Pending Tickets</button>
      <table>
        <thead>
          <tr>
            <th>Ticket ID</th>
            <th>Amount</th>
            <th>Description</th>
            <th>Status</th>
            <th>Actions</th>
          </tr>
        </thead>
        <tbody>
          {pendingTickets.map(ticket => (
            <tr key={ticket.id}>
              <td>{ticket.id}</td>
              <td>{ticket.amount}</td>
              <td>{ticket.description}</td>
              <td>{ticket.status}</td>
              <td>
                <button onClick={() => handleProcessTicket(ticket.id, 'Approved')}>Approve</button>
                <button onClick={() => handleProcessTicket(ticket.id, 'Denied')}>Deny</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ManagerAccount;

package com.example.TicketingSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TicketingSystem.entity.TicketEntity;
import com.example.TicketingSystem.repository.TicketRepository;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    /**
     * Submit a new ticket.
     *
     * @param ticket
     * @return
     */
    public TicketEntity submit(TicketEntity ticket) {
        // Set default status to "Pending"
        ticket.setStatus("Pending");
        return ticketRepository.save(ticket);
    }

    /**
     * Retrieve all tickets.
     *
     * @return 
     */
    public List<TicketEntity> getTickets() {
        return ticketRepository.findAll();
    }

    /**
     * Retrieve all pending tickets.
     *
     * @return 
     */
    public List<TicketEntity> getPendingTickets() {
        return ticketRepository.findByStatus("Pending");
    }

    /**
     * Process a ticket by approving or denying.
     *
     * @param ticketId
     * @param status   
     * @return 
     */
    public TicketEntity processTicket(Integer ticketId, String status) {
        Optional<TicketEntity> ticketOptional = ticketRepository.findById(ticketId);

        if (ticketOptional.isEmpty()) {
            throw new IllegalArgumentException("Ticket not found with ID: " + ticketId);
        }

        TicketEntity ticket = ticketOptional.get();

        if (!"Pending".equals(ticket.getStatus())) {
            throw new IllegalArgumentException("Ticket has already been processed and cannot be updated.");
        }

        ticket.setStatus(status);
        return ticketRepository.save(ticket);
    }
}




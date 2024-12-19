package com.example.TicketingSystem.service;

import java.util.List;

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
     * Method to submit a new ticket.
     *
     * @param ticket The ticket data to be submitted.
     * @return The saved ticket.
     */
    public TicketEntity submit(TicketEntity ticket) {
        // Save ticket to the database
        return ticketRepository.save(ticket);
    }

    // In TicketService.java
public List<TicketEntity> getTickets() {
    return ticketRepository.findAll();
}

}



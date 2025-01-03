package com.example.TicketingSystem.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TicketingSystem.entity.TicketEntity;
import com.example.TicketingSystem.service.TicketService;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    // Endpoint to submit a new ticket
    @PostMapping("/submit")
    public ResponseEntity<?> createTicket(@RequestBody TicketEntity ticket) {
        if (ticket.getDescription() == null || ticket.getDescription().trim().isEmpty()
                || ticket.getAmount() == null) {
            return ResponseEntity.status(400).body("Error: All fields are required.");
        }

        try {
            TicketEntity savedTicket = ticketService.submit(ticket);
            return ResponseEntity.ok(savedTicket);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }

    // Endpoint to retrieve all tickets
    @GetMapping("/get-tickets")
    public ResponseEntity<List<TicketEntity>> getTickets() {
        List<TicketEntity> tickets = ticketService.getTickets();
        return ResponseEntity.ok(tickets);
    }

    // Endpoint to retrieve pending tickets
    @GetMapping("/pending")
    public ResponseEntity<List<TicketEntity>> getPendingTickets() {
        try {
            List<TicketEntity> pendingTickets = ticketService.getPendingTickets();
            return ResponseEntity.ok(pendingTickets);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PatchMapping("/process/{id}")
    public ResponseEntity<?> processTicket(@PathVariable Integer id, @RequestBody TicketEntity ticket) {
        try {
            // Fetch and process the ticket
            TicketEntity updatedTicket = ticketService.processTicket(id, ticket.getStatus());
            return ResponseEntity.ok(updatedTicket);
    
        } catch (IllegalArgumentException e) {
            
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }
    }
    
}

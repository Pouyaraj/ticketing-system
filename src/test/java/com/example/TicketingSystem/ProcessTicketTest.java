package com.example.TicketingSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.TicketingSystem.entity.TicketEntity;
import com.example.TicketingSystem.repository.TicketRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class ProcessTicketTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    private Integer ticketId;

    @BeforeEach
    public void setUp() {
        // Clear the repository before each test
        ticketRepository.deleteAll();

        // Create and save a sample ticket with "Pending" status
        TicketEntity ticket = new TicketEntity(100, "Business trip reimbursement", "Pending");
        TicketEntity savedTicket = ticketRepository.save(ticket);
        ticketId = savedTicket.getId(); // Store the ticket ID for later use in tests
    }

    @Test
    public void testProcessTicket_Approve_Success() throws Exception {
        // Perform PATCH request to approve the ticket
        mockMvc.perform(patch("/tickets/process/{id}", ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"Approved\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Approved"))
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.description").value("Business trip reimbursement"));
    }

    @Test
    public void testProcessTicket_Deny_Success() throws Exception {
        // Perform PATCH request to deny the ticket
        mockMvc.perform(patch("/tickets/process/{id}", ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"Denied\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("Denied"))
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.description").value("Business trip reimbursement"));
    }

    @Test
    public void testProcessTicket_Fail_TicketNotFound() throws Exception {
        // Perform PATCH request with an invalid ticket ID
        mockMvc.perform(patch("/tickets/process/{id}", 9999) // Non-existent ticket ID
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"Approved\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Ticket not found with ID: 9999"));
    }

    @Test
    public void testProcessTicket_Fail_AlreadyProcessed() throws Exception {
        // First approve the ticket
        mockMvc.perform(patch("/tickets/process/{id}", ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"Approved\"}"))
                .andExpect(status().isOk());

        // Try to process the same ticket again (should fail since it's already processed)
        mockMvc.perform(patch("/tickets/process/{id}", ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"Denied\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Ticket has already been processed and cannot be updated."));
    }

    @Test
    public void testProcessTicket_Fail_InvalidStatus() throws Exception {
        // Perform PATCH request with an invalid status (not "Approved" or "Denied")
        mockMvc.perform(patch("/tickets/process/{id}", ticketId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"status\": \"InvalidStatus\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Status is required."));
    }
}

package com.example.TicketingSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.TicketingSystem.entity.TicketEntity;
import com.example.TicketingSystem.repository.TicketRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class GetPendingTicketsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @BeforeEach
    public void setUp() {
        // Clear the repository before each test
        ticketRepository.deleteAll();

        // Create and save sample tickets
        TicketEntity ticket1 = new TicketEntity(100, "Business trip reimbursement", "Pending");
        TicketEntity ticket2 = new TicketEntity(200, "Office supplies reimbursement", "Pending");
        TicketEntity ticket3 = new TicketEntity(300, "Travel expenses reimbursement", "Approved");

        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
        ticketRepository.save(ticket3);
    }

    @Test
    public void testGetPendingTickets_Success() throws Exception {
        // Perform GET request to retrieve all pending tickets
        mockMvc.perform(get("/tickets/pending")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].amount").value(100))
                .andExpect(jsonPath("$[0].description").value("Business trip reimbursement"))
                .andExpect(jsonPath("$[0].status").value("Pending"))
                .andExpect(jsonPath("$[1].amount").value(200))
                .andExpect(jsonPath("$[1].description").value("Office supplies reimbursement"))
                .andExpect(jsonPath("$[1].status").value("Pending"));
    }

    @Test
    public void testGetPendingTickets_NoPendingTickets() throws Exception {
        // Clear the repository and create only approved tickets for this test
        ticketRepository.deleteAll();
        TicketEntity ticket1 = new TicketEntity(300, "Travel expenses reimbursement", "Approved");
        TicketEntity ticket2 = new TicketEntity(400, "Training reimbursement", "Approved");

        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);

        // Perform GET request when there are no pending tickets
        mockMvc.perform(get("/tickets/pending")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}

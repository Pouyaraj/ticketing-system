package com.example.TicketingSystem;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.TicketingSystem.controller.TicketController;
import com.example.TicketingSystem.entity.TicketEntity;
import com.example.TicketingSystem.repository.TicketRepository;
import com.example.TicketingSystem.service.TicketService;

@SpringBootTest
@AutoConfigureMockMvc
public class SubmitTicketTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TicketController ticketController;

    // Sample data for testing
    private TicketEntity testTicket;

    @BeforeEach
    public void setUp() {
        // Clear the repository before each test
        ticketRepository.deleteAll();

        // Create and save a test ticket
        testTicket = new TicketEntity(100, "Business trip reimbursement", "Pending");
        ticketRepository.save(testTicket);
    }

    @Test
    public void testSubmitTicket_Success() throws Exception {
        // Prepare ticket submission request
        String ticketJson = "{\"amount\":100,\"description\":\"Business trip reimbursement\"}";

        // Perform the POST request to submit the ticket
        mockMvc.perform(post("/tickets/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.description").value("Business trip reimbursement"))
                .andExpect(jsonPath("$.status").value("Pending"));
    }

    @Test
    public void testSubmitTicket_Failure_MissingAmount() throws Exception {
        // Prepare ticket submission request with missing amount
        String ticketJson = "{\"description\":\"Business trip reimbursement\"}";

        // Perform the POST request
        mockMvc.perform(post("/tickets/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Error: All fields are required."));
    }

    @Test
    public void testSubmitTicket_Failure_MissingDescription() throws Exception {
        // Prepare ticket submission request with missing description
        String ticketJson = "{\"amount\":100}";

        // Perform the POST request
        mockMvc.perform(post("/tickets/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Error: All fields are required."));
    }

    @Test
    public void testSubmitTicket_Failure_EmptyDescription() throws Exception {
        // Prepare ticket submission request with empty description
        String ticketJson = "{\"amount\":100,\"description\":\"\"}";

        // Perform the POST request
        mockMvc.perform(post("/tickets/submit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ticketJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").value("Error: All fields are required."));
    }
}

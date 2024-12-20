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

import com.example.TicketingSystem.controller.TicketController;
import com.example.TicketingSystem.entity.TicketEntity;
import com.example.TicketingSystem.repository.TicketRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class GetAllTicketsTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TicketController ticketController;

    @BeforeEach
    public void setUp() {
        // Clear the repository before each test
        ticketRepository.deleteAll();

        // Create and save sample tickets
        TicketEntity ticket1 = new TicketEntity(100, "Business trip reimbursement", "Pending");
        TicketEntity ticket2 = new TicketEntity(200, "Office supplies reimbursement", "Pending");
        ticketRepository.save(ticket1);
        ticketRepository.save(ticket2);
    }

    @Test
    public void testGetAllTickets_Success() throws Exception {
        // Perform GET request to retrieve all tickets
        mockMvc.perform(get("/tickets/get-tickets")
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
    public void testGetAllTickets_NoTickets() throws Exception {
        // Clear the repository for this test
        ticketRepository.deleteAll();

        // Perform GET request when there are no tickets
        mockMvc.perform(get("/tickets/get-tickets")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }
}

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

import com.example.TicketingSystem.controller.accountController;
import com.example.TicketingSystem.entity.AccountEntity;
import com.example.TicketingSystem.repository.AccountRepository;
import com.example.TicketingSystem.service.AccountService;

@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private accountController accountController;

    // Sample data for testing
    private AccountEntity testUser;

    @BeforeEach
    public void setUp() {
        // Clear the repository before each test
        accountRepository.deleteAll();

        // Create and save a test user
        testUser = new AccountEntity("testUser", "password123", "John", "Doe");
        accountRepository.save(testUser);
    }

    @Test
    public void testLogin_Success() throws Exception {
        // Prepare login request
        String loginJson = "{\"username\":\"testUser\",\"password\":\"password123\"}";

        // Perform the POST request
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testUser"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    public void testLogin_Failure_InvalidUsername() throws Exception {
        // Prepare login request with invalid username
        String loginJson = "{\"username\":\"invalidUser\",\"password\":\"password123\"}";

        // Perform the POST request
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Error: Invalid username or password."));
    }

    @Test
    public void testLogin_Failure_InvalidPassword() throws Exception {
        // Prepare login request with invalid password
        String loginJson = "{\"username\":\"testUser\",\"password\":\"wrongPassword\"}";

        // Perform the POST request
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Error: Invalid username or password."));
    }

    @Test
    public void testLogin_Failure_MissingUsername() throws Exception {
        // Prepare login request with missing username
        String loginJson = "{\"password\":\"password123\"}";

        // Perform the POST request
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Error: Username and password are required."));
    }

    @Test
    public void testLogin_Failure_MissingPassword() throws Exception {
        // Prepare login request with missing password
        String loginJson = "{\"username\":\"testUser\"}";

        // Perform the POST request
        mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(loginJson))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$").value("Error: Username and password are required."));
    }
}

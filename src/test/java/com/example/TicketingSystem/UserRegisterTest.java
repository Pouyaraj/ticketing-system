package com.example.TicketingSystem;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

public class UserRegisterTest {
    ApplicationContext app;
    HttpClient webClient;
    ObjectMapper objectMapper;

    /**
     * Before every test, restart the Spring Boot app, and initialize the HttpClient and ObjectMapper.
     */
    @BeforeEach
    public void setUp() throws InterruptedException {
        webClient = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        String[] args = new String[]{};
        app = SpringApplication.run(TicketingSystemApplication.class, args); // Replace with your main application class
        Thread.sleep(500); // Wait for the app to initialize
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        Thread.sleep(500); // Wait for the app to shut down
        SpringApplication.exit(app);
    }

    /**
     * Test: Sending an HTTP POST request to /register with valid data.
     * 
     * Expected Response:
     *  - Status Code: 200
     *  - Response Body: JSON representation of the registered user
     */
    @Test
    public void registerUserSuccessful() throws IOException, InterruptedException {
        String json = """
                {
                    "username": "user1",
                    "password": "password123",
                    "firstName": "John",
                    "lastName": "Doe"
                }
                """;
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(200, status, "Expected Status Code 200 - Actual Code was: " + status);
    }

    /**
     * Test: Sending an HTTP POST request to /register with a duplicate username.
     * 
     * Expected Response:
     *  - Status Code: 409
     */
    @Test
    public void registerUserDuplicateUsername() throws IOException, InterruptedException {
        String json = """
                {
                    "username": "user1",
                    "password": "password123",
                    "firstName": "John",
                    "lastName": "Doe"
                }
                """;

        // First registration request
        HttpRequest firstPostRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();

        // Second registration request with the same username
        HttpResponse<String> response2 = webClient.send(firstPostRequest, HttpResponse.BodyHandlers.ofString());
        int status2 = response2.statusCode();
        Assertions.assertEquals(409, status2, "Expected Status Code 409 - Actual Code was: " + status2);
    }

    /**
     * Test: Sending an HTTP POST request to /register with invalid data.
     * 
     * Expected Response:
     *  - Status Code: 400
     */
    @Test
    public void registerUserInvalidInput() throws IOException, InterruptedException {
        String json = """
                {
                    "username": "",
                    "password": "pass",
                    "firstName": "",
                    "lastName": "Doe"
                }
                """;

        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/register"))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .header("Content-Type", "application/json")
                .build();
        HttpResponse<String> response = webClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        int status = response.statusCode();
        Assertions.assertEquals(400, status, "Expected Status Code 400 - Actual Code was: " + status);
        Assertions.assertEquals("Error: All fields are required.", response.body(), "Unexpected response body");
    }
}

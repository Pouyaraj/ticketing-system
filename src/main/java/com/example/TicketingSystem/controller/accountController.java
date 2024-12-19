package com.example.TicketingSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TicketingSystem.entity.AccountEntity;
import com.example.TicketingSystem.service.AccountService;

@RestController
@RequestMapping
@CrossOrigin(origins = "http://localhost:3000")
public class accountController {
    private final AccountService accountService;

    @Autowired
    public accountController(AccountService accountService){
        this.accountService = accountService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountEntity user) {
        // Validate input fields
        if (user.getUsername() == null || user.getUsername().trim().isEmpty() ||
            user.getPassword() == null || user.getPassword().length() < 4 ||
            user.getFirstName() == null || user.getFirstName().trim().isEmpty() ||
            user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            return ResponseEntity.status(400).body("Error: All fields are required.");
        }
    
        try {
            // Call the service to register the user
            AccountEntity savedUser = accountService.register(user);
            return ResponseEntity.ok(savedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body("The username has already been registered. Please choose another one.");
        }
    }
    

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AccountEntity user) {
        if (user.getUsername() == null || user.getPassword() == null) {
            return ResponseEntity.status(401).body("Error: Username and password are required.");
        }
    
        try {
            AccountEntity verifiedAccount = accountService.login(user);
            return ResponseEntity.ok(verifiedAccount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(401).body("Error: Invalid username or password.");
        }
    }
    
}

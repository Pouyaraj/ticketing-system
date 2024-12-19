package com.example.TicketingSystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component; // Adjust if needed

import com.example.TicketingSystem.entity.AccountEntity;
import com.example.TicketingSystem.repository.AccountRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;

    public DataInitializer(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if there is already a user with the username "Admin"
        if (!accountRepository.existsByUsername("Admin")) {
            AccountEntity admin = new AccountEntity();
            admin.setFirstName("AdminFirstName");
            admin.setLastName("AdminLastName");
            admin.setUsername("Admin");
            admin.setPassword("password");
            admin.setRole("Manager");

            accountRepository.save(admin); 
            System.out.println("Default Admin user created.");
        }
    }
}


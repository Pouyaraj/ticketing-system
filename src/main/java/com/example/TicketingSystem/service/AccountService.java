package com.example.TicketingSystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TicketingSystem.entity.AccountEntity;
import com.example.TicketingSystem.repository.AccountRepository;
import com.example.TicketingSystem.repository.TicketRepository;

@Service
public class AccountService {

    private final TicketRepository ticketRepository;

    private final AccountRepository usertRepository;

    @Autowired
    public AccountService(TicketRepository ticketRepository, AccountRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.usertRepository = userRepository;
    }

    public AccountEntity register(AccountEntity user) {
        AccountEntity existingAccount = usertRepository.findByUsername(user.getUsername());

        // If the user exists, throw an exception to prevent registration
        if (existingAccount != null) {
            throw new IllegalArgumentException("Username already exists. Please choose a different username.");
        }

        if (user.getRole() == null || user.getRole().trim().isEmpty()) {
            user.setRole("Employee");
        }

        // Save the new user
        return usertRepository.save(user);
    }

    public AccountEntity login(AccountEntity user) {
        // Find user by username
        Optional<AccountEntity> existingUser = Optional.ofNullable(usertRepository.findByUsername(user.getUsername()));

        if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
            return existingUser.get();
        }
        throw new IllegalArgumentException("Invalid username or password!");
    }
}

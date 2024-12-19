package com.example.TicketingSystem.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketingSystem.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {
    AccountEntity findByUsername(String username);
    Optional<AccountEntity> findByUsernameAndPassword(String username, String password);
    boolean existsByUsername(String username);
}


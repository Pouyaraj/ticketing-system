package com.example.TicketingSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketingSystem.entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {
}

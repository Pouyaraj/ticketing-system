package com.example.TicketingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketingSystem.entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {

    /**
     * Find all tickets by status.
     *
     * @param status 
     * @return 
     */
    List<TicketEntity> findByStatus(String status);
}

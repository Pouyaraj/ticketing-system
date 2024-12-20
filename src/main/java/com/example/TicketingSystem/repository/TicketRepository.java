package com.example.TicketingSystem.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TicketingSystem.entity.TicketEntity;

public interface TicketRepository extends JpaRepository<TicketEntity, Integer> {

    /**
     * Find all tickets by status.
     *
     * @param status The status of the tickets to retrieve (e.g., "Pending").
     * @return A list of tickets with the specified status.
     */
    List<TicketEntity> findByStatus(String status);
}

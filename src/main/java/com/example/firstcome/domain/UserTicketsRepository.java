package com.example.firstcome.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserTicketsRepository extends JpaRepository<UserTickets, Long> {

    List<UserTickets> findByEventId(Long eventId);
}

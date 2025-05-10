package com.example.firstcome.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_tickets")
@Getter
@NoArgsConstructor
public class UserTickets {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private Long eventId;
    private Long eventSeatId;

    public UserTickets(String userId, Long eventId, Long eventSeatId) {
        this.userId = userId;
        this.eventId = eventId;
        this.eventSeatId = eventSeatId;
    }
}

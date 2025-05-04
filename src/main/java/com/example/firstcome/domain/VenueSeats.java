package com.example.firstcome.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity(name = "venue_seats")
@Getter
public class VenueSeats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long venueId = 1L;
    @Enumerated(EnumType.STRING)
    private SeatType type;
}

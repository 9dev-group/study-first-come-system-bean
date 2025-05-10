package com.example.firstcome.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueSeatsRepository extends JpaRepository<VenueSeats, Long> {

    List<VenueSeats> findByVenueId(Long venueId);
    VenueSeats findByIdAndVenueId(Long id, Long venueId);
}

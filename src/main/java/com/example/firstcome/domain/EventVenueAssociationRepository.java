package com.example.firstcome.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventVenueAssociationRepository extends JpaRepository<EventVenueAssociation, Long> {

    EventVenueAssociation findByEventId(Long eventId);

}

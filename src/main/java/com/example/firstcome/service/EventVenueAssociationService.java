package com.example.firstcome.service;

import com.example.firstcome.domain.EventVenueAssociationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventVenueAssociationService {

    private final EventVenueAssociationRepository eventVenueAssociationRepository;

    @Transactional(readOnly = true)
    public Long getVenueId(Long eventId) {
        return eventVenueAssociationRepository.findByEventId(eventId).getVenueId();
    }
}

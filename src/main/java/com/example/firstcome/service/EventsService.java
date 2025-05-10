package com.example.firstcome.service;

import com.example.firstcome.domain.Events;
import com.example.firstcome.domain.EventsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class EventsService {

    private final EventsRepository eventsRepository;

    public int getSeatCount(Long eventId) {
        var event = eventsRepository.findById(eventId)
                .orElseThrow(() -> new NoSuchElementException("Not Found EventId: " + eventId));
        return event.getVipNumber() + event.getNormalNumber();
    }

    public Events getEventWithLock(Long eventId) {
        return eventsRepository.findByIdWithLock(eventId);
    }
}

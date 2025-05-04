package com.example.firstcome.service;

import com.example.firstcome.domain.UserTicketsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTicketsService {

    private final UserTicketsRepository userTicketsRepository;

    @Transactional(readOnly = true)
    public List<Long> getAllByEventId(Long eventId) {
        return userTicketsRepository.findByEventId(eventId)
                .stream()
                .map(ticket -> ticket.getEventSeatId())
                .toList();
    }

}

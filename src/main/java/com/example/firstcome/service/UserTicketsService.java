package com.example.firstcome.service;

import com.example.firstcome.domain.UserTickets;
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

    @Transactional
    public void createTicket(String userId, Long eventId, Long eventSeatId, int limitCount) {
        var currentCount = userTicketsRepository.findByEventId(eventId).size();
        if (limitCount == currentCount) {
            throw new IllegalStateException("ticketing done");
        }

        userTicketsRepository.save(new UserTickets(userId, eventId, eventSeatId));
    }

    @Transactional
    public void createTicketV2(String userId, Long eventId, Long eventSeatId) {
        userTicketsRepository.save(new UserTickets(userId, eventId, eventSeatId));
    }

}

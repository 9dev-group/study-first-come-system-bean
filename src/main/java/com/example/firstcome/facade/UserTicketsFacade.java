package com.example.firstcome.facade;

import com.example.firstcome.domain.VenueSeats;
import com.example.firstcome.service.EventVenueAssociationService;
import com.example.firstcome.service.EventsService;
import com.example.firstcome.service.UserTicketsService;
import com.example.firstcome.service.VenueSeatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserTicketsFacade {

    private final EventsService eventsService;
    private final UserTicketsService userTicketsService;
    private final VenueSeatsService venueSeatsService;
    private final EventVenueAssociationService eventVenueAssociationService;


    @Transactional
    public void buyTicket(String userId, Long eventId, Long eventSeatId) {
        Long venueId = eventVenueAssociationService.getVenueId(eventId);
        if (venueId == null) {
            throw new IllegalArgumentException("bad request (eventId)");
        }
        VenueSeats seats = venueSeatsService.get(eventSeatId, venueId);
        if (seats == null) {
            throw new IllegalArgumentException("bad request (eventSeatId)");
        }
        // event에 x lock을 겁니다. 이유는 userTickets에 lock을 걸게되면 gap lock 발생될 수 있습니다.
        // event에 lock을 거는건 좋은 방법은 아닙니다. 모든 요청의 lock이 1곳에 집중되어 병목이 발생합니다.
        // mysql 인프라의 한계
        // MySQL 8.0.19 에서는 원자성 보장 쿼리를 제공합니다. 이런경우가 아니면 지금과 같이 lock으로 처리해야 합니다.
        eventsService.getEventWithLock(eventId);
        int limitCount = eventsService.getSeatCount(eventId);
        try {
            userTicketsService.createTicket(userId, eventId, eventSeatId, limitCount);
        } catch (DataIntegrityViolationException ex) {
            log.error("unique validation (eventId, userId) or (eventid, eventSeatId)");
            throw new IllegalStateException("already ticket");
        }
    }
}

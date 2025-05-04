package com.example.firstcome.facade;

import com.example.firstcome.dto.response.EventSeatsResponse;
import com.example.firstcome.service.EventVenueAssociationService;
import com.example.firstcome.service.UserTicketsService;
import com.example.firstcome.service.VenueSeatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetSeatFacade {

    private final EventVenueAssociationService eventVenueAssociationService;
    private final VenueSeatsService venueSeatsService;
    private final UserTicketsService userTicketsService;

    public EventSeatsResponse getSeat(Long eventId) {
        var venueId = eventVenueAssociationService.getVenueId(eventId);
        var tickets = userTicketsService.getAllByEventId(eventId);
        var seatResponses = venueSeatsService.getAvailableSeats(venueId, tickets)
                .stream()
                .map(seat -> new EventSeatsResponse.SeatResponse(
                        seat.getId(), seat.getType().name()
                )).toList();

        return new EventSeatsResponse(eventId, seatResponses);
    }
}

package com.example.firstcome.facade;

import com.example.firstcome.dto.response.EventSeatsResponse;
import com.example.firstcome.service.EventVenueAssociationService;
import com.example.firstcome.service.UserTicketsService;
import com.example.firstcome.service.VenueSeatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetSeatFacade {

    private final EventVenueAssociationService eventVenueAssociationService;
    private final VenueSeatsService venueSeatsService;
    private final UserTicketsService userTicketsService;
    private final RedisTemplate<String, String> stringRedisTemplate;

    private static final String KEY = "event:";

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

    public EventSeatsResponse getSeatV2(Long eventId) {
        var opsForSet = stringRedisTemplate.opsForSet();
        var seats = opsForSet.members(KEY + eventId + ":status:available");

        var seatResponses = seats.stream()
                .map(seat -> {
                    var seatArray = seat.split(":");
                    return new EventSeatsResponse.SeatResponse(
                            Long.valueOf(seatArray[0]), seatArray[1]
                    );
                }).toList();


        return new EventSeatsResponse(eventId, seatResponses);
    }
}

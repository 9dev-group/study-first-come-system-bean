package com.example.firstcome.facade;

import com.example.firstcome.service.EventVenueAssociationService;
import com.example.firstcome.service.VenueSeatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InitSeatFacade {

    private final VenueSeatsService venueSeatsService;
    private final EventVenueAssociationService eventVenueAssociationService;
    private final RedisTemplate<String, String> customStringRedisTemplate;
    private final static String KEY = "event:";

    @Transactional(readOnly = true)
    public void init(Long eventId) {
        var venueId = eventVenueAssociationService.getVenueId(eventId);
        var seats = venueSeatsService.getAvailableSeats(venueId, List.of())
                .stream()
                .map(seat -> {
                    String value = seat.getId() + ":" + seat.getType().name();
                    return value;
                })
                .toArray(String[]::new);
        var opsForSet = customStringRedisTemplate.opsForSet();
        opsForSet.add(KEY + eventId + ":status:available", seats);
    }

}

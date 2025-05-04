package com.example.firstcome.service;

import com.example.firstcome.domain.VenueSeats;
import com.example.firstcome.domain.VenueSeatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VenueSeatsService {

    private final VenueSeatsRepository venueSeatsRepository;

    @Transactional(readOnly = true)
    public List<VenueSeats> getAvailableSeats(Long venueId, List<Long> excludeSeatIds) {
        Map<Long, Long> excludeSeatIdByMap = excludeSeatIds.stream()
                .collect(Collectors.toMap(Function.identity(), Function.identity()));

        return venueSeatsRepository.findByVenueId(venueId)
                .stream()
                .filter(seat -> excludeSeatIdByMap.get(seat.getId()) == null)
                .toList();
    }

}

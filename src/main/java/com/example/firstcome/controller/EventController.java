package com.example.firstcome.controller;

import com.example.firstcome.dto.response.EventSeatsResponse;
import com.example.firstcome.facade.GetSeatFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final GetSeatFacade getSeatFacade;

    @GetMapping("/{eventId}/seats")
    public EventSeatsResponse getSeats(
            @PathVariable Long eventId
    ) {
        return getSeatFacade.getSeat(eventId);
    }

}

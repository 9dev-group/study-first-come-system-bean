package com.example.firstcome.controller;

import com.example.firstcome.dto.request.CreateTicketRequest;
import com.example.firstcome.dto.response.EventSeatsResponse;
import com.example.firstcome.facade.GetSeatFacade;
import com.example.firstcome.facade.UserTicketsFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final UserTicketsFacade userTicketsFacade;
    private final GetSeatFacade getSeatFacade;

    @GetMapping("/{eventId}/seats")
    public EventSeatsResponse getSeats(
            @PathVariable Long eventId
    ) {
        return getSeatFacade.getSeat(eventId);
    }

    @PostMapping("/{eventId}/tickets")
    public void createTicket(
            @PathVariable Long eventId,
            @RequestBody CreateTicketRequest request
    ) {
        userTicketsFacade.buyTicket(request.userId(), eventId, request.eventSeatId());
    }

}

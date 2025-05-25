package com.example.firstcome.controller;

import com.example.firstcome.domain.SeatType;
import com.example.firstcome.dto.request.CreateTicketRequest;
import com.example.firstcome.dto.response.EventSeatsResponse;
import com.example.firstcome.facade.GetSeatFacade;
import com.example.firstcome.facade.InitSeatFacade;
import com.example.firstcome.facade.UserTicketsFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/events")
@RequiredArgsConstructor
public class EventController {

    private final UserTicketsFacade userTicketsFacade;
    private final GetSeatFacade getSeatFacade;
    private final InitSeatFacade initSeatFacade;
    private final RedisTemplate<String, String> customStringRedisTemplate;

    @GetMapping("{seatId}")
    public void test(
            @PathVariable Long seatId
    ) {
        customStringRedisTemplate.opsForSet()
                .remove("event:1:status:available", seatId + ":" + SeatType.NORMAL.name());
    }

    @GetMapping("/{eventId}/seats")
    public EventSeatsResponse getSeats(
            @PathVariable Long eventId
    ) {
        return getSeatFacade.getSeat(eventId);
    }

    @GetMapping("/{eventId}/new-seats")
    public EventSeatsResponse getSeatsV2(
            @PathVariable Long eventId
    ) {
        return getSeatFacade.getSeatV2(eventId);
    }

    @PostMapping("/{eventId}/tickets")
    public void createTicket(
            @PathVariable Long eventId,
            @RequestBody CreateTicketRequest request
    ) {
        userTicketsFacade.buyTicket(request.userId(), eventId, request.eventSeatId());
    }

    @PostMapping("/{eventId}/new-tickets")
    public void createTicketV2(
            @PathVariable Long eventId,
            @RequestBody CreateTicketRequest request
    ) {
        userTicketsFacade.buyTicketV2(request.userId(), eventId, request.eventSeatId());
    }

    @PostMapping("/{eventId}/new-tickets-v3")
    public void createTicketV3(
            @PathVariable Long eventId,
            @RequestBody CreateTicketRequest request
    ) {
        userTicketsFacade.buyTicketV3(request.userId(), eventId, request.eventSeatId());
    }

    @PostMapping("/{eventId}/seats/init")
    public void initSeats(
            @PathVariable Long eventId
    ) {
        initSeatFacade.init(eventId);
    }

}

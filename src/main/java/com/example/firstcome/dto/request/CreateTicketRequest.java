package com.example.firstcome.dto.request;

public record CreateTicketRequest(
        String userId,
        Long eventSeatId
) {
}

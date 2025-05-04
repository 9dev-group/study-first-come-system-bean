package com.example.firstcome.dto.response;

import java.util.List;

public record EventSeatsResponse(
        Long eventId,
        List<SeatResponse> seats
) {

    public record SeatResponse(
            Long id,
            String type
    ) {
    }

}

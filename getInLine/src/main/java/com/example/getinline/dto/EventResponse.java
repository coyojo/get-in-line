package com.example.getinline.dto;

import com.example.getinline.constant.EventStatus;

import java.time.LocalDateTime;

public record EventResponse(
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDatetime,
        LocalDateTime eventEndDatetime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo
) {
    public static EventResponse of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventResponse(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public static EventResponse from(EventDTO eventDTO){
        if (eventDTO == null){
            return null;
        }
        return EventResponse.of(
                eventDTO.placeId(),
                eventDTO.eventName(),
                eventDTO.eventStatus(),
                eventDTO.eventStartDatetime(),
                eventDTO.eventEndDatetime(),
                eventDTO.currentNumberOfPeople(),
                eventDTO.capacity(),
                eventDTO.memo()

        );

    }
}

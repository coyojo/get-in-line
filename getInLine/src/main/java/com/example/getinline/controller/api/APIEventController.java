package com.example.getinline.controller.api;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.APIDataResponse;
import com.example.getinline.dto.EventRequest;
import com.example.getinline.dto.EventResponse;
import com.example.getinline.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class APIEventController {
    private final EventService eventService;
    @GetMapping("/events")  //전체 리스트 조회
    public APIDataResponse<List<EventResponse>> getEvents(
            @Positive Long placeId,
            @Size(min = 2) String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime
    ) {
        List<EventResponse> eventResponses = eventService.getEvents(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime
        ).stream().map(EventResponse::from).toList();

        return APIDataResponse.of(eventResponses);
        //eventSevice에 있는 getEvents는 리턴값으로 EventDto를 내보내므로 우리가 원하는 EventResponse 값으로 바꿔줘야한다.
        // response는 원하는 응답에 따라 바꿔 줄 수 있어야 하는데 DTO는 데이터 그 자체이므로 바뀌지 말아햐 하므로
        // DTO로 나온 결과값을 EventResponse로 바꿔주는 로직을  Response 클래스에 만드는것이 효율적
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")  // 이벤트 생성
    public APIDataResponse<String> createEvent(@Valid @RequestBody EventRequest eventRequest){
       log.debug("보고싶은 값:{}", eventRequest);
       boolean result = eventService.createEvent(eventRequest.toDTO());
        return APIDataResponse.of(Boolean.toString(result));

       // return true;
    }

    @GetMapping("/events/{eventId}")  // 단건 조회
    public APIDataResponse<EventResponse> getEvent(@PathVariable Long eventId) {
       /* if (eventId.equals(2L)) {
            return APIDataResponse.empty();
        }
        return APIDataResponse.of(EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        ));*/
        EventResponse eventResponse = EventResponse.from(eventService.getEvent(eventId).orElse(null));
        return APIDataResponse.of(eventResponse);

    }

    @PutMapping("events/{eventId}") // 수정
    public APIDataResponse<String> modifyEvent(
            @PathVariable Long eventId,
            @RequestBody EventRequest eventRequest
    ) {
        boolean result = eventService.modifyEvent(eventId, eventRequest.toDTO());
        return APIDataResponse.of(Boolean.toString(result));
    }

    @DeleteMapping("/events/{eventId}")// 삭제
    public APIDataResponse<String> removeEvent(@PathVariable Long eventId) {
        boolean result = eventService.removeEvent(eventId);

        return APIDataResponse.of(Boolean.toString(result));
    }



}



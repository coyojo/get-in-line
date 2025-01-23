package com.example.getinline.controller.api;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.APIDataResponse;
import com.example.getinline.dto.EventRequest;
import com.example.getinline.dto.EventResponse;
import com.example.getinline.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class APIEventController {
    private final EventService eventService;
    @GetMapping("/events")  //전체 리스트 조회
    public APIDataResponse<List<EventResponse>> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) {
        List<EventResponse> response = eventService
                .getEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime)
                .stream().map(EventResponse::from).toList();
        //eventSevice에 있는 getEvents는 리턴값으로 EventDto를 내보내므로 우리가 원하는 EventResponse 값으로 바꿔줘야한다.
        // response는 원하는 응답에 따라 바꿔 줄 수 있어야 하는데 DTO는 데이터 그 자체이므로 바뀌지 말아햐 하므로
        // DTO로 나온 결과값을 EventResponse로 바꿔주는 로직을  Response 클래스에 만드는것이 효율적
        return APIDataResponse.of(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")  // 이벤트 생성
    public APIDataResponse<Void> createEvent(@RequestBody EventRequest eventRequest){
        return APIDataResponse.empty();

       // return true;
    }

    @GetMapping("/events/{eventId}")  // 단건 조회
    public APIDataResponse<EventResponse> getEvent(@PathVariable Long eventId) {
        if (eventId.equals(2L)) {
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
        ));
    }

    @PutMapping("events/{eventId}") // 수정
    public APIDataResponse<Void> modifyEvent(
            @PathVariable Long eventId,
            @RequestBody EventRequest eventRequest
    ) {
        return APIDataResponse.empty();
    }

    @DeleteMapping("/events/{eventId}")// 삭제
    public APIDataResponse<Void> removeEvent(@PathVariable Long eventId) {
        return APIDataResponse.empty();
    }



}



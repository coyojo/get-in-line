package com.example.getinline.service;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.EventDTO;
import com.example.getinline.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/*
1. 전체 리스트 조회
2. 이벤트 생성
3. 특정 아이디를 통해 이벤트 정보 단건조회
4.수정
5.삭제

 */
@Service
@RequiredArgsConstructor
public class EventService { //필터링으로 장소 이름 상태 기간을 이용
   /*
   검색어들을 받아서 이벤트 리스트를 변환
   @Param PlaceId 장소 ID
   @Param eventName 이벤트 이름
   @Param eventStatus 이벤트 상태
   @Param eventStartDatetime 시작 시간
   @Param eventEndDatetime 종료시간

    */

    private final EventRepository eventRepository;

    //1. 전체 리스트 조회
    public List<EventDTO> getEvents(Long placeId,
                                     String eventName,
                                     EventStatus eventStatus,
                                     LocalDateTime eventStartDatetime,
                                     LocalDateTime eventEndDatetime
    ) {
        return eventRepository.findEvents();
    }

    //2. 단건 조회
    public Optional<EventDTO> findEvent(Long eventId){
        return Optional.empty();
    }



    //3.이벤트 생성
    public boolean createEvent(EventDTO eventDTO){
        return true;
    }

    //3.이벤트 수정
    public boolean modifyEvent(Long eventId,EventDTO eventDTO){
        //eventId를 받아서 그 대상을 수정한 후 eventDTO로 내보내는 메서드
        return true;
    }

    public boolean removeEvent(Long eventId){
        //eventId를 삭제하는 메서드
        return true;
    }

}

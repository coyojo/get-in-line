package com.example.getinline.service;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

/*
1. 전체 리스트 조회
2. 이벤트 생성
3. 특정 아이디를 통해 이벤트 정보 단건조회
4.수정
5.삭제

 */
public interface EventService {  //필터링으로 장소 이름 상태 기간을 이용
   /*
   검색어들을 받아서 이벤트 리스트를 변환
   @Param PlaceId 장소 ID
   @Param eventName 이벤트 이름
   @Param eventStatus 이벤트 상태
   @Param eventStartDatetime 시작 시간
   @Param eventEndDatetime 종료시간

    */

    List<EventDTO> findEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    );
}

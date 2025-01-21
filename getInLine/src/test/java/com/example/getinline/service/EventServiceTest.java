package com.example.getinline.service;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.EventDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.awaitility.Awaitility.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


class EventServiceTest {
    private EventService sut;

    @DisplayName("검색 조건 없이 검색하면 전체 이벤트 리스트를 출력하여 보여준다")
    @Test
    void getEventsTest() {
        //given

        //when
         List<EventDTO> list = sut.getEvents(null,null,null,null,null);
        assertThat(list).hasSize(2);

        //then

    }

    @DisplayName("검색 조건과 함께 이벤트 검색하면 검색된 결과를 출력하여 보여준다")
    @Test
    void getEventsTest2() {
        //given
        Long placeId = 1L;
        String eventName = "오전 운동";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDatetime = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        LocalDateTime eventEndDatetime = LocalDateTime.of(2021, 1, 2, 0, 0, 0);

        //when
        List<EventDTO> list = sut.getEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);
        //then
        assertThat(list)
                .hasSize(1)  //검색 조건에 맞는 이벤트 하나일때 하나만 보여주기
                .allSatisfy(event -> {
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("palcdId", placeId)
                            .hasFieldOrPropertyWithValue("eventName", eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus);
                    assertThat(event.eventStartDatetime()).isAfterOrEqualTo(eventStartDatetime); //검색한 내용의 시작시간이 내 검색어의 시작 시간보다 더 뒤에있거나 같은때
                    assertThat(event.eventEndDatetime()).isBeforeOrEqualTo(eventEndDatetime);
                    });// 조건에 맞는 이벤트가 여러개 있을때는 해당 assertion을 다 수행한다.
        }

    @Test
    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력하여 보여준다")
    void findEventTest(){
        //given
        Long eventId = 1L;
        EventDTO eventDTO = createEventDTO(1L,"오전운동",true);

        //when
        Optional<EventDTO> result = sut.findEvent(eventId);

        //then
        assertThat(result).hasValue(eventDTO);
    }



    @Test
    @DisplayName("이벤트 ID로 이벤트를 조회하면, 결과값이 없을때 빈 정보를 출력하여 보여준다")
    void findEventTest2(){
        //given
        Long eventId = 2L;
       // given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());

        //when
        Optional<EventDTO> result = sut.findEvent(eventId);

        //then
        assertThat(result).isEmpty();
      //  verify(eventRepository).findEvent(eventId);
    }


    @Test
    void createEvent() {
    }

    @Test
    @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true로 보여준다")
    void modifyEvent() {
        //given
        Long eventId = 1L;
        EventDTO dto = createEventDTO(1L,"오후운동",false);

        //when
        boolean result = sut.modifyEvent(eventId,dto);

        //then
        assertThat(result).isTrue();
       // verify(eventRepository).update(eventId,dto);
    }

    @Test
    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false로 보여준다")
    void modifyEventNoEventId() {
        //given
        EventDTO dto = createEventDTO(1L,"오후운동",false);
       // given(eventRepository.updateEvent(null,dto)).willReturn(false);
        //when
        boolean result = sut.modifyEvent(null,dto);

        //then
        assertThat(result).isFalse();
    }
    @Test
    @DisplayName("이벤트 ID는 주는데 변경할 정보를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false로 보여준다")
    void modifyEventNoDto() {
        //given
        Long eventId = 1L;
       // given(eventRepository.updateEvent(eventId,null)).willReturn(false);
        //when
        boolean result = sut.modifyEvent(eventId,null);

        //then
        assertThat(result).isFalse();
        //verify(eventRepository).updateEvent(eventId,null);
    }

    @Test
    @DisplayName("이벤트 ID를 주면, 이벤트 정보를 삭제하고 결과를 true로 보여준디")
    void removeEvent() {
        //given
        Long eventId = 1L;

        //when
        boolean result = sut.removeEvent(eventId);

        //then
        assertThat(result).isTrue();

    }


    @Test
    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 삭제 결과를 false로 보여준디")
    void removeFalse() {
        //given
        Long eventId = 1L;

        //when
        boolean result = sut.removeEvent(eventId);

        //then
        assertThat(result).isTrue();

    }


    private EventDTO createEventDTO(long placeId, String eventName, boolean isMorning) {
        String hourStart = isMorning ? "09" : "13";
        String hourEnd = isMorning ? "12" : "16";

        return createEventDTO(
                placeId,
                eventName,
                EventStatus.OPENED,
                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourStart)),
                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourEnd))
        );
    }

    private EventDTO createEventDTO(
            long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDateTime,
            LocalDateTime eventEndDateTime
    ) {
        return EventDTO.of(
                placeId,
                eventName,
                eventStatus,
                eventStartDateTime,
                eventEndDateTime,
                0,
                24,
                "마스크 꼭 착용하세요",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
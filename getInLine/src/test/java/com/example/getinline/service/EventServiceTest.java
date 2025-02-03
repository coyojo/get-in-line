package com.example.getinline.service;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.EventDTO;
import com.example.getinline.exception.GeneralException;
import com.example.getinline.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {
    @InjectMocks private EventService sut;
    @Mock private EventRepository eventRepository;
    //이렇게 해주면 Mokito가 eventRepository를 우라가 테스트 하고자 하는 sut에 주입해준다


    @DisplayName("검색 조건 없이 검색하면 전체 이벤트 리스트를 출력하여 보여준다")
    @Test
    void getEventsTest() {
        //given
        //이렇게 다 null값일 때 아래의 리스트 2개를 리턴해달라는 의미이다.
        given(eventRepository.findEvents(null,null,null,null,null))
                .willReturn(List.of(
                        createEventDTO(1L, "오전 운동", true),
                        createEventDTO(1L, "오후 운동", false)
                ));
    //when
         List<EventDTO> list = sut.getEvents(null,null,null,null,null);


        //then

        assertThat(list).hasSize(2);
        //verify는 given과 다르게 ()밖에서 repository에 있는 메서드를 적어준다.
       // verify(eventRepository).findEvents(null,null,null,null,null);
        //verify(mock, times(1)).someMethod("some arg") 즉 1번 호출됬다는 역할을 한다.
        // BBD 모키토 방식으로 쓰면 아래와 같은 코드를 쓰고 이건 바로 윗줄의 verify 코드와 같은 수행을 한다
        then(eventRepository).should().findEvents(null,null,null,null,null);


    }

    @DisplayName("검색 조건과 함께 이벤트 검색하면 검색된 결과 하나를 출력하여 보여준다")
    @Test
    void getEventsTest2() {
        //given
        Long placeId = 1L;
        String eventName = "오전 운동";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDatetime = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        LocalDateTime eventEndDatetime = LocalDateTime.of(2021, 1, 2, 0, 0, 0);

        given(eventRepository.findEvents(placeId,eventName,eventStatus,eventStartDatetime,eventEndDatetime))
                .willReturn(List.of(
                        createEventDTO(1L,"오전 운동",eventStatus,eventStartDatetime,eventEndDatetime)
                        ));

        //when
        List<EventDTO> list = sut.getEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);
        //then
        assertThat(list)
                .hasSize(1)  //검색 조건에 맞는 이벤트 하나일때 하나만 보여주기
                .allSatisfy(event -> {
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("placeId", placeId)
                            .hasFieldOrPropertyWithValue("eventName", eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus);
                    assertThat(event.eventStartDatetime()).isAfterOrEqualTo(eventStartDatetime); //검색한 내용의 시작시간이 내 검색어의 시작 시간보다 더 뒤에있거나 같은때
                    assertThat(event.eventEndDatetime()).isBeforeOrEqualTo(eventEndDatetime);
                    });// 조건에 맞는 이벤트가 여러개 있을때는 해당 assertion을 다 수행한다.
        then(eventRepository).should().findEvents(placeId,eventName,eventStatus,eventStartDatetime,eventEndDatetime);
    }

    @Test
    @DisplayName("이벤트 ID로 존재하는 이벤트를 조회하면, 해당 이벤트 정보를 출력하여 보여준다")
    void findEventTest(){
        //given
        Long eventId = 1L;
        EventDTO eventDTO = createEventDTO(1L,"오전운동",true);
       //존재하는 이벤트 조회니까 eventDTO를 Optional에 넣어서 보내준다.
        given(eventRepository.findEvent(eventId)).willReturn(Optional.of(eventDTO));
        //when
        Optional<EventDTO> result = sut.findEvent(eventId);

        //then
        assertThat(result).hasValue(eventDTO);
        then(eventRepository).should().findEvent(eventId);
    }



    @Test
    @DisplayName("이벤트 ID로 이벤트를 조회하면, 결과값이 없을때 빈 정보를 출력하여 보여준다")
    void findEventTest2(){
        //given
        Long eventId = 2L;
        given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());

        //when
        Optional<EventDTO> result = sut.findEvent(eventId);

        //then
        assertThat(result).isEmpty();
      verify(eventRepository).findEvent(eventId);
    }


    @Test
    @DisplayName("이벤트 정보를 주면, 이벤트를 생성하고 결과를 true로 보여준다.")
    void createEvent() {
        //given
        EventDTO dto = createEventDTO(1L,"오후 운동", false);
        given(eventRepository.insertEvent(dto)).willReturn(true);

        //when
        boolean result = sut.createEvent(dto);

        //then
        assertThat(result).isTrue();
        verify(eventRepository).insertEvent(dto);

    }

    @Test
    @DisplayName("이번트 정보를 주지 않으면, 이벤트를 생성 중단하고 결과를 false로 보여준다.")
    void createfalseEvent() {
        //given
        given(eventRepository.insertEvent(null)).willReturn(false);

        //when
        boolean result = sut.createEvent(null);

        //then
        assertThat(result).isFalse();
       // verify(eventRepository).insertEvent(null);
        then(eventRepository).should().insertEvent(null);
    }

    @Test
    @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true로 보여준다")
    void modifyEvent() {
        //given
        Long eventId = 1L;
        EventDTO dto = createEventDTO(1L,"오후운동",false);
        given(eventRepository.updateEvent(eventId,dto)).willReturn(true);
        //when
        boolean result = sut.modifyEvent(eventId,dto);

        //then
        assertThat(result).isTrue();
       // verify(eventRepository).update(eventId,dto);
        then(eventRepository).should().updateEvent(eventId,dto);
    }

    @Test
    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false로 보여준다")
    void modifyEventNoEventId() {
        //given
        EventDTO dto = createEventDTO(1L,"오후운동",false);
        given(eventRepository.updateEvent(null,dto)).willReturn(false);
        //when
        boolean result = sut.modifyEvent(null,dto);

        //then
        assertThat(result).isFalse();
        then(eventRepository).should().updateEvent(null,dto);
    }
    @Test
    @DisplayName("이벤트 ID는 주는데 변경할 정보를 주지 않으면, 이벤트 정보 변경 중단하고 결과를 false로 보여준다")
    void modifyEventNoDto() {
        //given
        Long eventId = 1L;
       given(eventRepository.updateEvent(eventId,null)).willReturn(false);
        //when
        boolean result = sut.modifyEvent(eventId,null);

        //then
        assertThat(result).isFalse();
        //verify(eventRepository).updateEvent(eventId,null);
        then(eventRepository).should().updateEvent(eventId,null);

    }

    @Test
    @DisplayName("이벤트 ID를 주면, 이벤트 정보를 삭제하고 결과를 true로 보여준디")
    void removeEvent() {
        //given
        Long eventId = 1L;
        given(eventRepository.deleteEvent(eventId)).willReturn(true);

        //when
        boolean result = sut.removeEvent(eventId);

        //then
        assertThat(result).isTrue();
        then(eventRepository).should().deleteEvent(eventId);

    }


    @Test
    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 삭제 결과를 false로 보여준디")
    void removeFalse() {
        //given
        given(eventRepository.deleteEvent(null)).willReturn(false);
        //when
        boolean result = sut.removeEvent(null);

        //then
        assertThat(result).isFalse();
        then(eventRepository).should().deleteEvent(null);

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



    @DisplayName("이벤트 검색하는데 에러가 발생한 경우, 줄서기 프로젝트 기본에러로 전환하여 예외 던진다")
    @Test
    void givenDataRelatedException_whenSearchingEvents_thenThrowsGeneralException() {
        //given
        RuntimeException e = new RuntimeException("This is test.");
        given(eventRepository.findEvents(any(),any(),any(),any(),any()))
                .willThrow(e);
        //when
        Throwable thrown = catchThrowable(() -> sut.getEvents(null,null,null,null,null));



        //then

        assertThat(thrown)
                .isInstanceOf(GeneralException.class)
                        .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
        then(eventRepository).should().findEvents(any(),any(),any(),any(),any());


    }



}
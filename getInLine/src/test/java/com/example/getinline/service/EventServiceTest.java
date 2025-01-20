package com.example.getinline.service;

import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.EventDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;



class EventServiceTest {
    private EventService sut;

    @DisplayName("검색 조건 없이 검색하면 전체 이벤트 리스트를 출력하여 보여준다")
    @Test
    void getEventsTest() {
        //given

        //when
         List<EventDTO> list = sut.getEvents(null,null,null,null,null){
            assertThat(list).hasSize(2);
        }
        //then

    }

    @DisplayName("검색 조건과 함께 이벤트 검색하면 검색된 결과를 출력하여 보여준다")
    @Test
    void getEventsTest2(){
        //given
        Long placeId = 1L;
        String eventName = "오전 운동";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDatetime = LocalDateTime.of(2021,1,1,0,0,0);
        LocalDateTime eventEndDatetime = LocalDateTime.of(2021,1,2,0,0,0);

        //when
        List<EventDTO> list = sut.getEvents(placeId,eventName,eventStatus,eventStartDatetime,eventEndDatetime){
            //then
            assertThat(list)
                    .hasSize(1)  //검색 조건에 맞는 이벤트 하나일때 하나만 보여주기
                    .allSatisfy(event -> {
                        assertThat(event)
                                .hasFieldOrPropertyWithValue("", "")
                    });// 조건에 맞는 이벤트가 여러개 있을때는 해당 assertion을 다 수행한다.
        }

    }

    @Test
    void createEvent() {
    }

    @Test
    void modifyEvent() {
    }

    @Test
    void removeEvent() {
    }
}
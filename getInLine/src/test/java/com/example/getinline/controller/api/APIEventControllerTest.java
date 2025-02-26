package com.example.getinline.controller.api;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.constant.EventStatus;
import com.example.getinline.dto.EventDTO;
import com.example.getinline.dto.EventResponse;
import com.example.getinline.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.BDDAssumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(APIEventController.class)
class APIEventControllerTest {
    private final MockMvc mvc;
    private final ObjectMapper mapper;
    @MockitoBean private EventService eventService;

    public APIEventControllerTest(
            @Autowired MockMvc mvc,
            @Autowired ObjectMapper mapper
    ) {
        this.mvc = mvc;
        this.mapper = mapper;
    }
    @DisplayName("[API][GET] 이벤트 리스트 조회 + 검색 파라미터")
    @Test
    void getList() throws Exception {
        // Given
        given(eventService.getEvents(any(),any(),any(),any(),any())).willReturn(List.of(createEventDTO()));

        // When & Then
        //QnuertParam으로 검색 조건을 주면
        mvc.perform(get("/api/events")
                        .queryParam("placeId","1")  //pueryParam value값은 String만 가능
                        .queryParam("eventName","오후")
                        .queryParam("eventStatus",EventStatus.OPENED.name())
                        .queryParam("eventStartDatetime","2021-01-01T00:00:00")
                        .queryParam("eventEndDatetime","2021-01-02T00:00:00")

                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].placeId").value(1L))
                .andExpect(jsonPath("$.data[0].eventName").value("오후 운동"))
                .andExpect(jsonPath("$.data[0].eventStatus").value(EventStatus.OPENED.name()))
                .andExpect(jsonPath("$.data[0].eventStartDatetime").value(LocalDateTime
                        .of(2021, 1, 1, 13, 0, 0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data[0].eventEndDatetime").value(LocalDateTime
                        .of(2021, 1, 1, 17, 0, 0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data[0].currentNumberOfPeople").value(0))
                .andExpect(jsonPath("$.data[0].capacity").value(24))
                .andExpect(jsonPath("$.data[0].memo").value("마스크 꼭 착용하세요"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));

        then(eventService).should().getEvents(any(),any(),any(),any(),any());

    }


    //validation test
    @DisplayName("[API][GET] 이벤트 리스트 조회 + 잘못된 검색 파라미터")
    @Test
    void getListFalse() throws Exception {
        // Given
        given(eventService.getEvents(any(),any(),any(),any(),any())).willReturn(List.of(createEventDTO()));

        // When & Then
        //QnuertParam으로 검색 조건을 주면
        mvc.perform(get("/api/events")
                        .queryParam("placeId","0")  //pueryParam value값은 String만 가능
                        .queryParam("eventName","오")
                        .queryParam("eventStatus",EventStatus.OPENED.name())
                        .queryParam("eventStartDatetime","2021-01-01T00:00:00")
                        .queryParam("eventEndDatetime","2021-01-02T00:00:00")

                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.VALIDATION_ERROR.getMessage())));

        then(eventService).shouldHaveNoInteractions();

    }




    @DisplayName("[API][POST] 이벤트 생성")
    @Test
    void givenEvent_whenCreatingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        EventResponse eventResponse = EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        );
        given(eventService.createEvent(any())).willReturn(true);
        // When & Then
        mvc.perform(
                        post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(eventResponse))
                )
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }


    @DisplayName("[API][POST] 이벤트 생성 - 잘못된 데이터 입력")
    @Test
    void givenWrongEvent_whenCreatingAnEvent_thenReturnsFailedStandardResponse() throws Exception {
        // Given
        EventResponse eventResponse = EventResponse.of(
                -1L,
                "  ",
                null,
                null,
                null,
                -1,
                0,
                "마스크 꼭 착용하세요"
        );

        // When & Then
        mvc.perform(
                        post("/api/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(eventResponse))
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.SPRING_BAD_REQUEST.getCode()))
                .andExpect(jsonPath("$.message").value(containsString(ErrorCode.SPRING_BAD_REQUEST.getMessage())));

        then(eventService).shouldHaveNoInteractions();
    }


    @DisplayName("[API][GET] 단일 이벤트 조회 - 이벤트 있는 경우, 이벤트 데이터를 담은 표준 API 출력")
    @Test
    void givenEventId_whenRequestingExistentEvent_thenReturnsEventInStandardResponse() throws Exception {
        // Given
        long eventId = 1L;
        // When & Then
        mvc.perform(get("/api/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isMap())
                .andExpect(jsonPath("$.data.placeId").value(1L))
                .andExpect(jsonPath("$.data.eventName").value("오후 운동"))
                .andExpect(jsonPath("$.data.eventStatus").value(EventStatus.OPENED.name()))
                .andExpect(jsonPath("$.data.eventStartDatetime").value(LocalDateTime
                        .of(2021, 1, 1, 13, 0, 0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data.eventEndDatetime").value(LocalDateTime
                        .of(2021, 1, 1, 16, 0, 0)
                        .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
                .andExpect(jsonPath("$.data.currentNumberOfPeople").value(0))
                .andExpect(jsonPath("$.data.capacity").value(24))
                .andExpect(jsonPath("$.data.memo").value("마스크 꼭 착용하세요"))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }
    @DisplayName("[API][GET] 단일 이벤트 조회 - 이벤트 없는 경우, 빈 표준 API 출력")
    @Test
    void givenEventId_whenRequestingNonexistentEvent_thenReturnsEmptyStandardResponse() throws Exception {
        // Given
        long eventId = 2L;
        // When & Then
        mvc.perform(get("/api/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.data").isEmpty())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }
    @DisplayName("[API][PUT] 이벤트 변경")
    @Test
    void givenEvent_whenModifyingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        long eventId = 1L;
        EventResponse eventResponse = EventResponse.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        );
        // When & Then
        mvc.perform(
                        put("/api/events/" + eventId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(eventResponse))
                )
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }
    @DisplayName("[API][DELETE] 이벤트 삭제")
    @Test
    void givenEvent_whenDeletingAnEvent_thenReturnsSuccessfulStandardResponse() throws Exception {
        // Given
        long eventId = 1L;
        // When & Then
        mvc.perform(delete("/api/events/" + eventId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.OK.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.OK.getMessage()));
    }

    private EventDTO createEventDTO()
            {
        return EventDTO.of(
                1L,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 17, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }


}
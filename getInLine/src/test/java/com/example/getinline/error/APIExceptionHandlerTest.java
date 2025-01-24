package com.example.getinline.error;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.dto.APIErrorResponse;
import com.example.getinline.exception.GeneralException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.handler.DispatcherServletWebRequest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


class APIExceptionHandlerTest {

    private APIExceptionHandler sut;
    private WebRequest webRequest;

    private HttpServletRequest httpServletRequest;

    @BeforeEach
    void setUP(){
        sut = new APIExceptionHandler();
    }

    @DisplayName("검증 오류 - 응답데이터 정의")
    @Test
    void givenValidationException_whenCallingValidation_thenReturnsResponseEntity(){
        //given
        ConstraintViolationException e = new ConstraintViolationException(Set.of());
        webRequest = new DispatcherServletWebRequest(new MockHttpServletRequest());
        //when
        ResponseEntity<Object> response =  sut.validation(e,webRequest);
        response.getBody();
        response.getHeaders();
        response.getStatusCode();
        //then
        assertThat(response)
                .hasFieldOrPropertyWithValue("body", APIErrorResponse.of(false, ErrorCode.VALIDATION_ERROR,e))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.BAD_REQUEST);
    }

    @DisplayName("프로젝트 일반 오류 - 응답 데이터 정의")
    @Test
    void givenGeneralException_whenCallingValidation_thenReturnsResponseEntity(){
        //given
        GeneralException e = new  GeneralException("test message");
        webRequest = new DispatcherServletWebRequest(new MockHttpServletRequest());
        //when
        ResponseEntity<Object> response =  sut.general(e,webRequest);
        response.getBody();
        response.getHeaders();
        response.getStatusCode();
        //then
        assertThat(response)
                .hasFieldOrPropertyWithValue("body", APIErrorResponse.of(false, e.getErrorCode(),e))
                .hasFieldOrPropertyWithValue("headers", HttpHeaders.EMPTY)
                .hasFieldOrPropertyWithValue("statusCode", e.getErrorCode().isClientSideError() ? HttpStatus.BAD_REQUEST : HttpStatus.INTERNAL_SERVER_ERROR);
    }





}
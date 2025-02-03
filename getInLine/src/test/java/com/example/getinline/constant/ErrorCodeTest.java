package com.example.getinline.constant;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ErrorCodeTest {


    @ParameterizedTest(name = "[{index}] {0} ==> {1}")// 테스트 코드가 애러코드를 제외하고는 계속 반복되므로 이걸 활용
    @MethodSource  // 입력소스로 메소드를 받겠다는것
    @DisplayName("예외를 받으면 예외 메세지가 포함된 메세지 출력")
    void givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage(ErrorCode error, String expected){
        //given
        Exception e = new Exception("This is test message.");

        //when
        String actual = error.getMessage(e);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    //ParameterizedTest 덕분에 6개의 에러코드 테스트가 한번에 끝남
    static Stream<Arguments> givenExceptionWithMessage_whenGettingMessage_thenReturnsMessage(){
        return Stream.of(
                arguments(ErrorCode.OK,"OK - This is test message."),
                arguments(ErrorCode.BAD_REQUEST,"bad request - This is test message."),
                arguments(ErrorCode.SPRING_BAD_REQUEST,"spring-detected bad request - This is test message."),
                arguments(ErrorCode.VALIDATION_ERROR,"validation error - This is test message."),
                arguments(ErrorCode.INTERNAL_ERROR,"internal error - This is test message."),
                arguments(ErrorCode.SPRING_INTERNAL_ERROR,"spring-detected internal error - This is test message."),
                arguments(ErrorCode.DATA_ACCESS_ERROR,"Data access error - This is test message.")
        );
    }

    @ParameterizedTest(name = "[{index}] {0} ==> {1}")// 테스트 코드가 애러코드를 제외하고는 계속 반복되므로 이걸 활용
    @MethodSource  // 입력소스로 메소드를 받겠다는것
    @DisplayName("에러 메세지를 받으면, 해당 에러 메세지를 출력")
    void givenMessage_whenGettingMessage_thenReturnsMessage(String input, String expected){


        //when
        String actual = ErrorCode.INTERNAL_ERROR.getMessage(input);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    //ParameterizedTest 덕분에 6개의 에러코드 테스트가 한번에 끝남
    static Stream<Arguments> givenMessage_whenGettingMessage_thenReturnsMessage(){
        return Stream.of(
                arguments(null,ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("",ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments(" ",ErrorCode.INTERNAL_ERROR.getMessage()),
                arguments("This is test message.","This is test message.")
        );
    }


    @DisplayName("toString() 호출 포맷")
    @Test
    void givenErrorCode_whenToString_thenReturnSimplifiedToString(){
        //given


        //when
        String result = ErrorCode.INTERNAL_ERROR.toString();
        //then
        assertThat(result).isEqualTo("INTERNAL_ERROR (20000)");

            }
}
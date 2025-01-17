package com.example.getinline.controller;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(BaseController.class)
class BaseControllerTest {

   // @Autowired
    private final MockMvc mvc;


    public BaseControllerTest(@Autowired MockMvc mvc){
    this.mvc = mvc;
    }

    @Test
    @DisplayName("[view][GET] 기본 페이지 요청테스트")
    void testRoot() throws Exception {
    //given



    //when & then
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())  // HttpStatus 정상인지 검사
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.TEXT_HTML)) // 뷰컨트롤러 테스트이므로 컨텐트 타입이 HTML 인지 테스트
                .andExpect(MockMvcResultMatchers.content().string(CoreMatchers.containsString("This is default page"))) // 컨텐츠 내용에 ()속의 내용이 포함되어 있는지 테스트
                .andExpect(MockMvcResultMatchers.view().name("index")); // 뷰네임 매핑테스트
                //.andDo(print());  //andDo(print()) 쓰면 에러가 안나도 결과를 출력해줌
    }
}
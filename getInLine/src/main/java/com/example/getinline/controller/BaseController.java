package com.example.getinline.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller   // view 관련된를 만들거니까 Controller 어노테이션 붙여주기
public class BaseController implements ErrorController {
    //Error페이지를 커스텀한 페이지로 쓰려먼 ErrorController를 implements 해줘야한다

    @GetMapping("/")
    public String root(){
        return "index";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }


}
package com.example.getinline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller   // view 관련된를 만들거니까 Controller 어노테이션 붙여주기
public class BaseController {

    @GetMapping("/")
    public String root(){
        return "index";
    }


}
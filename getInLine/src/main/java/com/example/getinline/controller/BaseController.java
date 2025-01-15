package com.example.getinline.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
@ControllerAdvice(basePackageClasses = BaseController.class) //BaseController만 적용한다는것이 아니라 BaseController가 속해있는 패키지 전부가 범위가 된다는 것이다
//@ExceptionHandler를 모아서 전체 패키지에 적용할 때 쓰는 어노테이션이 @ControllerAdvice
@Controller   // view 관련된를 만들거니까 Controller 어노테이션 붙여주기
public class BaseController {
    //Error페이지를 커스텀한 페이지로 쓰려먼 ErrorController를 implements 해줘야한다

    @GetMapping("/")
    public String root() throws Exception{
        throw new Exception("테스트");
      //  return "index";
    }

  /*  @GetMapping("/error")
    public String error(){
        return "error";
    }
    */

}
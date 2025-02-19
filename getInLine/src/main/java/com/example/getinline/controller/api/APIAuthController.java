package com.example.getinline.controller.api;

import com.example.getinline.dto.APIDataResponse;
import com.example.getinline.dto.AdminRequest;
import com.example.getinline.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/api")
//@RestController
public class APIAuthController {

    //RestController는 뷰를 찾는게 아니라 return 내용을 body에 그대로 전달해줌
    @PostMapping("/sign-up")
    public APIDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {

        return APIDataResponse.empty();
    }
    @PostMapping("/login")
    public APIDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return APIDataResponse.empty();
    }
}

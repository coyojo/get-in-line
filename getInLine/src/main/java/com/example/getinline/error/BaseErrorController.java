package com.example.getinline.error;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.dto.APIErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class BaseErrorController implements ErrorController {
    @GetMapping(value = "/error",produces = MediaType.TEXT_HTML_VALUE)
    //produces에 MediaType.TEXT_HTML_VALUE를 쓰면 text_html을 accept header로 가지고 있는것만 대응
    public ModelAndView errorHtml(HttpServletResponse response){
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;
        //errorCode가 4xx이면 ErrorCode.BAD_REQUEST이고 아니면 INTER_ERROR이라는 뜻
        return new ModelAndView("error",
                Map.of(
                        "statusCode",status.value(),
                        "errorCode", errorCode,
                       "message", errorCode.getMessage(status.getReasonPhrase())
                ),
                status
        );
    }

    @GetMapping("/error")
    public ResponseEntity<APIErrorResponse> error(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError() ? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;
        //errorCode가 4xx이면 ErrorCode.BAD_REQUEST이고 아니면 INTER_ERROR이라는 뜻
        return ResponseEntity
                .status(status)
                .body(APIErrorResponse.of(false, errorCode));
        }
    }




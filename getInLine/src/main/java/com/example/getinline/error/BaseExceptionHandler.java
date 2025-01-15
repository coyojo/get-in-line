package com.example.getinline.error;

import com.example.getinline.constant.ErrorCode;
import com.example.getinline.dto.APIErrorResponse;
import com.example.getinline.exception.GeneralException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//뷰에대한 ExceptionHandler
@ControllerAdvice
public class BaseExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<APIErrorResponse> general(HttpServletResponse response){
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        ErrorCode errorCode = status.is4xxClientError()? ErrorCode.BAD_REQUEST : ErrorCode.INTERNAL_ERROR;
        return ResponseEntity.status(status)
                .body(APIErrorResponse.of(false,errorCode));
    }

}

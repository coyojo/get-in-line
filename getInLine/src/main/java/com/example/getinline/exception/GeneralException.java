package com.example.getinline.exception;

import com.example.getinline.constant.ErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException {
    //RuntimeException 을 상속받아 메서드 재정의, 에러코드를 넣어주기 위해서 재정의
    private final ErrorCode errorCode;

    public GeneralException() {
        super(ErrorCode.INTERNAL_ERROR.getMessage());
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(String message) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(message));
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(String message, Throwable cause) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(message), cause);
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(Throwable cause) {
        super(ErrorCode.INTERNAL_ERROR.getMessage(cause));
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCode errorCode, String message) {
        super(errorCode.getMessage(message));
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCode errorCode, String message, Throwable cause) {
        super(errorCode.getMessage(message), cause);
        this.errorCode = errorCode;
    }

    public GeneralException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(cause), cause);
        this.errorCode = errorCode;
    }

}
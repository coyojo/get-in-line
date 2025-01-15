package com.example.getinline.dto;

import com.example.getinline.constant.ErrorCode;
import lombok.*;

@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)  //밖에서는 쓸 수 없고 상속에는 열려있어야 하므로 protected
public class APIErrorResponse {  //api가 내보내줄 응답을 미리 정해주는것

    //final을 붙여주는 이유는 해당 필드 값을 변경할 수 없게 보장합니다.
    private  final Boolean success;
    private  final Integer errorCode;
    private  final String message;

    // 생성자 메서드들
    public static APIErrorResponse of(Boolean success, Integer errorCode, String message){
        return new APIErrorResponse(success,errorCode,message);
    }

    public static APIErrorResponse of(Boolean success, ErrorCode errorCode){
        return new APIErrorResponse(success,errorCode.getCode(), errorCode.getMessage());

    }
    public static APIErrorResponse of(Boolean success, ErrorCode errorCode, Exception e){
        return new APIErrorResponse(success,errorCode.getCode(), errorCode.getMessage(e));

    }
    public static APIErrorResponse of(Boolean success, ErrorCode errorCode,String message){
        return new APIErrorResponse(success,errorCode.getCode(), errorCode.getMessage(message));

    }
}

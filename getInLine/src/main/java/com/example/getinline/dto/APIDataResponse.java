package com.example.getinline.dto;

import com.example.getinline.constant.ErrorCode;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode(callSuper = true)
public class APIDataResponse<T> extends APIErrorResponse {
    private final T data;

    //정상 상황을 가정한 응답일 경우
    private APIDataResponse(T data){
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }


    public static<T> APIDataResponse<T> of(T data){
        return new APIDataResponse<>(data);
    }

    //of 메서드는 new 키워드를 통해 객체를 직접 생성하지 않게 함으로써,
    // 객체 생성 방식을 내부에 감추고 더 많은 제어를 가능하게 합니다.
    // 예를 들어, 객체 생성 시 추가적인 검증이나 로직을 포함할 수 있습니다.


    public static <T> APIDataResponse<T> empty() {
        return new APIDataResponse<>(null);
    }

}

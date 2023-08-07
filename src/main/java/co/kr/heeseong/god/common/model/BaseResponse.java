package co.kr.heeseong.god.common.model;

import lombok.Getter;

@Getter
public class BaseResponse {

    private int code;
    private String message;
    private Object data;

    public BaseResponse() {
    }

    public BaseResponse(Object data) {
        this(200, "성공", data);
    }

    public BaseResponse(int code, String message) {
        this(code, message, null);
    }

    public BaseResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}

package co.kr.heeseong.god.common.model;

import lombok.Getter;

@Getter
public class BaseResponse {

    private int code;
    private String message;
    private Object data;

}

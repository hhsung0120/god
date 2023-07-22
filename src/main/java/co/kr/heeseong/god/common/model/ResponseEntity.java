package co.kr.heeseong.god.common.model;

import lombok.Getter;

@Getter
public class ResponseEntity {

    private int code;
    private String message;
    private Object data;
}

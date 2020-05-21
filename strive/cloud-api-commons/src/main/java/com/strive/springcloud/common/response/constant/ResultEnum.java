package com.strive.springcloud.common.response.constant;

public enum ResultEnum {
    SUCCESS(1,"success"),SYSTEM_ERROR(-1,"未知错误");

    private Integer code;

    private String msg;

    private ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

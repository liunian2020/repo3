package com.strive.springcloud.exception;

public class BusinessException  extends RuntimeException {
    /**
 *
 */
private static final long serialVersionUID = 1401195426008045913L;

    private Integer errorCode;

    private Object data;

    private String msg;

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(Integer errorCode, Object data) {
        this.data = data;
        this.errorCode = errorCode;
    }

    public BusinessException(Integer errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BusinessException(Integer errorCode, String msg) {
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public BusinessException(Integer errorCode, String msg, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public BusinessException(Integer errorCode, String msg, Object data) {
        this.errorCode = errorCode;
        this.msg = msg;
        this.data = data;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package com.strive.springcloud.util;

import com.strive.springcloud.common.response.ResponseResult;
import com.strive.springcloud.common.response.constant.ResultEnum;

public class ResponseUtil {
    public static <T> ResponseResult<T> success(T object) {
        ResponseResult<T> ResponseResult = new ResponseResult<T>();
        ResponseResult.setResultCode(ResultEnum.SUCCESS.getCode());
        ResponseResult.setResultMessage(ResultEnum.SUCCESS.getMsg());
        ResponseResult.setResult(object);
        return ResponseResult;
    }

    /**
     * 操作成功不返回消息
     *
     * @return
     */
    // @SuppressWarnings({ "rawtypes" })
    public static ResponseResult<Object> success() {
        return success(null);
    }

    /**
     * 操作失败返回的消息
     *
     * @param code
     * @param msg
     * @return
     */
    // @SuppressWarnings({ "rawtypes" })
    public static ResponseResult<Object> error(Integer code, String msg) {
        ResponseResult<Object> ResponseResult = new ResponseResult<Object>();
        ResponseResult.setResultCode(code);
        ResponseResult.setResultMessage(msg);
        return ResponseResult;
    }

    /**
     * 操作失败返回消息，对error的重载
     *
     * @param resultEnum
     * @return
     */
    // @SuppressWarnings({ "rawtypes" })
    public static ResponseResult<Object> error(ResultEnum resultEnum) {
        ResponseResult<Object> ResponseResult = new ResponseResult<Object>();
        ResponseResult.setResultCode(resultEnum.getCode());
        ResponseResult.setResultMessage(resultEnum.getMsg());
        return ResponseResult;
    }
}

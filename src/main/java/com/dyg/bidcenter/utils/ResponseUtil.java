package com.dyg.bidcenter.utils;

import com.dyg.bidcenter.common.ResultCode;
import lombok.Data;
import lombok.ToString;

/**
 * @author merz
 * @Description:
 * @date 2019/1/2 21:27
 */
@Data
@ToString
public class ResponseUtil<T> {

    private int code;

    private String msg;

    private T data;

    public ResponseUtil() {
    }

    private ResponseUtil(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private ResponseUtil(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static ResponseUtil result(int code, String msg) {
        return new ResponseUtil(code, msg);
    }

    public static ResponseUtil result(ResultCode resultCode) {
        return new ResponseUtil(resultCode.getCode(), resultCode.getMsg());
    }

    public static <T> ResponseUtil<T> result(int code, String msg, T data) {
        return new ResponseUtil<>(code, msg, data);
    }

    public static <T> ResponseUtil<T> result(ResultCode resultCode, T data) {
        return new ResponseUtil<>(resultCode.getCode(), resultCode.getMsg(), data);
    }
}

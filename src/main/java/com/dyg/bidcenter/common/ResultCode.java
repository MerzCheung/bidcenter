package com.dyg.bidcenter.common;

public enum ResultCode {

    LOGIN_TIMEOUT(501, "登陆超时!"),

    PARAM_ERROR(502, "参数错误!"),

    UNAUTHORIZED(503, "没有权限!"),

    AUTHENTICATIONEXCEPTION(504, "认证失败!"),

    NOEXISTUSER(505, "获取用户信息失败!"),

    CODEERROR(506, "验证码错误!"),

    ERROR(500, "失败！"),

    SUCCESS(200, "成功！");

    private int code;

    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package com.prim.mall.common;

/**
 * 状态码 枚举类 status
 *
 * @author prim
 */
public enum ResultCode {
    /**
     * 表示请求成功
     */
    SUCCESS(0, "SUCCESS"),
    /**
     * 表示请求失败
     */
    ERROR(1, "ERROR"),

    /**
     * 校验错误
     */
    VALID_ERROR(502, "VALID_ERROR"),

    /**
     * 表示必须要登录
     */
    NEED_LOGIN(10, "NEED_LOGIN"),
    /**
     * 1
     */
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResultCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}

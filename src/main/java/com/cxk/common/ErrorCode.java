package com.cxk.common;



/**
 *
 */

public enum ErrorCode {

    SUCCESS(0, "成功");





    /**
     * 通用错误码
     */
    private final Integer code;

    /**
     * 状态码信息
     */

    private final String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}

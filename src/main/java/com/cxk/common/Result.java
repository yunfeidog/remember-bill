package com.cxk.common;

import lombok.Data;

/**
 * @description: 统一返回结果
 */
@Data
public class Result {
    //描述统一格式中的编码，用于区分操作，可以简化配置0或1表示成功失败
    private Integer code;
    //描述统一格式中的数据,可选属性
    private Object data;
    //描述统一格式中的消息，可选属性
    private String msg;



    public Result() {
    }

    public Result(Integer code,  Object data ,String msg) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(Integer code, Object data) {
        this.code = code;
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}

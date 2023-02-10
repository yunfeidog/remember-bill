package com.cxk.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 */
@Data
public class UserLoginRequest  {

    private String phone;
    private String userPassword;


}

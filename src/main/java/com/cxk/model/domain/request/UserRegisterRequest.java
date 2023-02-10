package com.cxk.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author yupi
 */
@Data
public class UserRegisterRequest implements Serializable {


    private String userName;

    private String phone;

    private String userPassword;

    private String checkPassword;



}

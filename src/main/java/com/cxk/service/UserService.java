package com.cxk.service;

import com.cxk.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author houyunfei
 * @description 针对表【tb_user(用户表)】的数据库操作Service
 * @createDate 2023-02-10 13:48:46
 */
public interface UserService extends IService<User> {

    /**
     * 用户登录
     *
     * @param phone        手机号
     * @param userPassword 密码
     * @param request     请求
     * @return 用户信息
     */

    User userlogin(String phone, String userPassword, HttpServletRequest request);


    /**
     * 用户注册
     *
     * @param userName      用户名
     * @param phone         手机号
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return 是否注册成功
     */
    Boolean register(String userName, String phone, String userPassword, String checkPassword);


    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);

}

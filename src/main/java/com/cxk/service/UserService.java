package com.cxk.service;

import com.cxk.model.domain.request.BillAddRequest;
import com.cxk.model.entity.Bill;
import com.cxk.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.format.annotation.DateTimeFormat;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
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

    User userLogin(String phone, String userPassword, HttpServletRequest request);


    /**
     * 用户注册
     *
     * @param userName      用户名
     * @param phone         手机号
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return 是否注册成功
     */
    Boolean userRegister(String userName, String phone, String userPassword, String checkPassword);


    /**
     * 用户脱敏
     *
     * @param originUser 原始用户
     * @return 脱敏后的用户
     */
    User getSafetyUser(User originUser);





}

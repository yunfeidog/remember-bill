package com.cxk.controller;

import com.cxk.common.Code;
import com.cxk.common.Result;
import com.cxk.model.entity.User;
import com.cxk.model.domain.request.UserLoginRequest;
import com.cxk.model.domain.request.UserRegisterRequest;
import com.cxk.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户模块管理接口")
@Slf4j
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result userRegister(@RequestBody UserRegisterRequest user) {
        //user为前端传来的json数据，通过@RequestBody注解，将json数据转换为User对象
        System.out.println("user = " + user);
        if (user == null) {
            //todo 处理异常
            return new Result(Code.ERR, "用户为空");
        }
        //获取前端传来的数据
        String userName = user.getUserName();
        String phone = user.getPhone();
        String userPassword = user.getUserPassword();
        String checkPassword = user.getCheckPassword();

        //判断数据是否为空
        if (StringUtils.isAnyBlank(userName, phone, userPassword, checkPassword)) {
            System.out.println("存在数据为空");
            //todo 处理异常
            return new Result(Code.ERR, "注册数据存在空白");
        }
        System.out.println("userName = " + userName);

        //调用service层的方法，将数据存入数据库
        Boolean flag = userService.userRegister(userName, phone, userPassword, checkPassword);

        Result result = new Result();
        if (flag) {
            result.setCode(Code.OK);
            result.setMsg("注册成功");
        } else {
            result.setCode(Code.ERR);
            result.setMsg("注册失败");
        }
        return result;
    }


    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result userLogin(@RequestBody UserLoginRequest user, HttpServletRequest request) {
        if (user == null) {
            //todo 处理异常
            return new Result(Code.ERR, "用户为空");
        }
        String phone = user.getPhone();
        String userPassword = user.getUserPassword();
        if (StringUtils.isAnyBlank(phone, userPassword)) {
            //todo 处理异常 存在数据为空
            return new Result(Code.ERR, "登录数据存在空白");
        }

        //用户登录
        User userLogin = userService.userLogin(phone, userPassword, request);
        Result result = new Result();
        if (userLogin != null) {
            result.setCode(Code.OK);
            result.setMsg("登录成功");
            result.setData(userLogin);
        } else {
            result.setCode(Code.ERR);
            result.setMsg("登录失败");
        }
        return result;
    }

    /**
     * 修改用户名
     */
    @PostMapping("/updateUserName")
    public Result updateUserName(@RequestBody User user) {
        if (user == null) {
            //todo 处理异常
            return new Result(Code.ERR, "用户为空");
        }
        String userName = user.getUserName();
        Integer userId = user.getId();
        log.info("userName = " + userName);
        if (StringUtils.isAnyBlank(userName, userId.toString())) {
            //todo 处理异常 存在数据为空
            return new Result(Code.ERR, "修改数据存在空白");
        }


        Boolean flag = userService.updateById(user);
        Result result = new Result();
        if (flag) {
            result.setCode(Code.OK);
            result.setMsg("修改成功");
        } else {
            result.setCode(Code.ERR);
            result.setMsg("修改失败");
        }
        return result;
    }


    /**
     * 用户退出
     */
    @GetMapping("/logout")
    public Result userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
        return new Result(Code.OK, "退出成功");
    }

}

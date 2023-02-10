package com.cxk.controller;

import com.cxk.common.Code;
import com.cxk.common.Result;
import com.cxk.model.domain.User;
import com.cxk.model.domain.request.UserLoginRequest;
import com.cxk.model.domain.request.UserRegisterRequest;
import com.cxk.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;



    @PostMapping("/register")
    public Result userRegister(@RequestBody UserRegisterRequest user){
        //user为前端传来的json数据，通过@RequestBody注解，将json数据转换为User对象
        System.out.println("user = " + user);
        if (user == null) {
            //todo 处理异常
            return null;
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
            return null;
        }
        System.out.println("userName = " + userName);

        //调用service层的方法，将数据存入数据库
        Boolean flag = userService.register(userName, phone, userPassword, checkPassword);

        Result result = new Result();
        if(flag){
            result.setCode(Code.REGISTER_OK);
            result.setMsg("注册成功");
        }else{
            result.setCode(Code.REGISTER_ERR);
            result.setMsg("注册失败");
        }
        return result;
    }


    @PostMapping("/login")
    public Result userLogin(@RequestBody UserLoginRequest user, HttpServletRequest request){
        if (user == null) {
            //todo 处理异常
            return null;
        }
        String phone = user.getPhone();
        String userPassword = user.getUserPassword();
        if (StringUtils.isAnyBlank(phone, userPassword)) {
            //todo 处理异常 存在数据为空
            return null;
        }

        //用户登录
        User userLogin = userService.userlogin(phone, userPassword, request);
        Result result = new Result();
        if(userLogin != null){
            result.setCode(Code.LOGIN_OK);
            result.setMsg("登录成功");
            result.setData(userLogin);
        }else{
            result.setCode(Code.LOGIN_ERR);
            result.setMsg("登录失败");
        }
        return result;



    }

}

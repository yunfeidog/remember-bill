package com.cxk.service;
import java.util.Date;

import com.cxk.model.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;


@Slf4j
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void userLogin() {
        User user = new User();
        user.setPhone("123456");
        user.setUserPassword("123456");
    }

    @Test
    void userRegister() {
        User user = new User();
        user.setPhone("123456");
        user.setUserPassword("123456");
        user.setUserName("蔡徐坤");

        Boolean result = userService.userRegister(user.getUserName(),user.getPhone(),user.getUserPassword(),user.getUserPassword());
        if (result) {
            System.out.println("注册成功");
            log.info("新注册用户id为：{}", user.getId());
        } else {
            System.out.println("注册失败");
        }



    }

    @Test
    void getSafetyUser() {
    }
}
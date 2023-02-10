package com.cxk.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cxk.model.domain.User;
import com.cxk.service.UserService;
import com.cxk.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.cxk.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author houyunfei
 * @description 针对表【tb_user(用户表)】的数据库操作Service实现
 * @createDate 2023-02-10 13:48:46
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {


    @Resource
    private UserMapper userMapper;

    /**
     * 盐值，混淆密码
     */
    private static final String SALT = "cxk";

    /**
     * 用户登录
     *
     * @param phone        手机号
     * @param userPassword 密码
     * @return 用户信息
     */
    @Override
    public User userlogin(String phone, String userPassword, HttpServletRequest request) {
        //校验
        if (StringUtils.isAnyBlank(phone, userPassword)) {
            //todo 抛出异常  存在参数为空
            return null;
        }
        //长度校验
        if (userPassword.length() < 4) {
            //todo 抛出异常  密码长度过短
            return null;
        }

        if (phone.length() < 4) {
            //todo 抛出异常  手机号长度过短
            return null;
        }

        //加密
        String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //查询手机号是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        queryWrapper.eq("user_password", md5Password);
        User user = userMapper.selectOne(queryWrapper);

        //判断用户是否存在
        if (user == null) {
            log.info("用户不存在");
            //todo 抛出异常  用户不存在
            return null;
        }

        //用户脱敏
        User safetyUser = getSafetyUser(user);

        //将用户信息存入session
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public Boolean register(String userName, String phone, String userPassword, String checkPassword) {

        //校验
        if (StringUtils.isAnyBlank(userName, phone, userPassword, checkPassword)) {
            //todo 抛出异常参数为空
            return false;
        }
        if (userPassword.length() < 4 || checkPassword.length() < 4) {
            //todo 抛出异常密码过短
            return false;
        }

        if (phone.length() < 4) {
            //todo 抛出异常手机号不正确 处理长度过短
            return false;
        }

        if (!userPassword.equals(checkPassword)) {
            //todo 抛出异常两次密码不一致
            return false;
        }

        //判断手机号是否已经注册
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("phone", phone);
        Long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            //todo 抛出异常手机号已经注册
            return false;
        }

        //对密码进行加密
        System.out.println(userPassword);
        String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        System.out.println(md5Password);
        //用户注册
        User user = new User();
        user.setUserName(userName);
        user.setPhone(phone);
        user.setUserPassword(md5Password);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            //todo 抛出异常注册失败
            return false;
        }
        return true;
    }

    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUserName(originUser.getUserName());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setCreateTime(originUser.getCreateTime());
        safetyUser.setUpdateTime(originUser.getUpdateTime());
        return safetyUser;

    }


}





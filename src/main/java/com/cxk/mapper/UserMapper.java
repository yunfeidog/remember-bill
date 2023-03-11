package com.cxk.mapper;

import com.cxk.model.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @description 针对表【tb_user(用户表)】的数据库操作Mapper
* @createDate 2023-02-10 13:48:46
* @Entity com.cxk.model.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

}





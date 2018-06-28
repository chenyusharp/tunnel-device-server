package com.tunnel.service;

import com.tunnel.bean.User;
import com.tunnel.bean.UserExample;
import com.tunnel.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户服务service
 *
 * @author xiazy
 * @create 2018-06-25 19:59
 **/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public User getMemberById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }


    public void updateUserInfoById(User user){
        userMapper.updateByPrimaryKeySelective(user);
    }
}

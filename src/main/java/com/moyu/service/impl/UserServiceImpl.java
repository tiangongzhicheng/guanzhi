package com.moyu.service.impl;

import com.moyu.entity.User;
import com.moyu.mapper.UserMapper;
import com.moyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/4/6.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User getUser(Integer id) {
        return userMapper.getUser(id);
    }

    public List<User> getUserByWhere(User user){
        return userMapper.getUserByWhere(user);
    }
}

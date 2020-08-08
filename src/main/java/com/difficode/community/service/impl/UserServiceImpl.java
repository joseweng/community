package com.difficode.community.service.impl;

import com.difficode.community.entity.User;
import com.difficode.community.mapper.UserMapper;
import com.difficode.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    public User getUserByUserId(int userId) {
        return userMapper.getUserByUserId(userId);
    }
}

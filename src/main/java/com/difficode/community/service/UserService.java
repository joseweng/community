package com.difficode.community.service;

import com.difficode.community.entity.User;

import java.util.Map;

public interface UserService {
    /*
    * */
    User getUserByUserId(int userId);
    Map<String,Object> register(User user);
    int activation(int id,String code);
    Map<String ,Object> login(String username,String password,long expiredTime);
    void logout(String ticket);
}

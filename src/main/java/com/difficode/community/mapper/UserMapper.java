package com.difficode.community.mapper;

import com.difficode.community.entity.User;

public interface UserMapper {
    User getUserByUserId(int userId);

    User getUserByUsername(String username);

    User getUserByEmail(String email);

    int saveUser(User user);

    int updateStatus(int id, int status);

    int updateHeaderUrl(int id,String headerUrl);
}

package com.house.userservice.mapper;

import com.house.userservice.bean.User;

import java.util.List;

public interface UserMapper {

    User selectById (Long id);

    List<User> select(User user);

    int insert(User user);

    int update(User user);

    User selectByEmail(String email);
}

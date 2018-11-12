package com.house.userservice.mapper;

import com.house.userservice.bean.User;

import java.util.List;

public interface UserMapper {

    User selectById (Long id);

    List<User> select(User user);
}

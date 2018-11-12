package com.house.userservice.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.house.userservice.bean.User;
import com.house.userservice.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/12
 * @ Description：
 * @ throws
 */

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Value("${file.prefix}")
    private String imgPrefix;

    /**
     * @ Author jmy
     * @ Description //TODO User
     * @ Date 2018/11/12
     * @ Param [id]
     * @ return com.house.userservice.bean.User
     * 1.首先通过缓存获取
     * 2.不存在将从通过数据库获取用户对象
     * 3.将用户对象写入缓存，设置缓存时间5分钟
     * 4.返回对象
     **/
    public User getUserById(Long id) {

        String key = "user:"+id;
        String json = redisTemplate.opsForValue().get(key);
        User user = null;
        if (Strings.isNullOrEmpty(json)){
            user = userMapper.selectById(id);
            user.setAvatar(imgPrefix+user.getAvatar());
            //缓存到redis
            String toJSON = JSON.toJSONString(user);
            redisTemplate.opsForValue().set(key, toJSON);
            redisTemplate.expire(key, 5, TimeUnit.MINUTES);
        } else {
            user = JSON.parseObject(json, User.class);
        }
        return user;
    }

    public List<User> getUserByUser(User user) {

        List<User> userList = userMapper.select(user);
        userList.forEach(u -> {
            u.setAvatar(imgPrefix+u.getAvatar());
        });
        return userList;
    }
}

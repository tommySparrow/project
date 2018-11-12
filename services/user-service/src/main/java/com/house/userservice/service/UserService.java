package com.house.userservice.service;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.house.userservice.bean.User;
import com.house.userservice.common.excection.UserException;
import com.house.userservice.mapper.UserMapper;
import com.house.userservice.utils.BeanHelper;
import com.house.userservice.utils.HashUtils;
import org.apache.commons.lang.RandomStringUtils;
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

    @Autowired
    private MailService mailService;

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

    //注册用户
    public boolean addUser(User user) {

        String enableUrl = user.getEnableUrl();
        String email = user.getEmail();
        user.setPasswd(HashUtils.encryPassword(user.getPasswd()));
        BeanHelper.onInsert(user);
        //插入数据
        userMapper.insert(user);

        //发送激活邮件
        registerNotify(email,enableUrl);
        return true;
    }

    /**
     * @ Author jmy
     * @ Description //TODO User
     * @ Date 2018/11/12
     * @ Param [email, enableUrl]
     * @ return void
     * 发送激活邮件
     **/
    private void registerNotify(String email, String enableUrl) {

        String randomKey = HashUtils.hashString(email)+ RandomStringUtils.random(10);
        //缓存到Rediszhong
        redisTemplate.opsForValue().set(randomKey, email);
        redisTemplate.expire(randomKey, 1, TimeUnit.HOURS);

        //发送邮件
        String content = enableUrl +"?key="+  randomKey;
        mailService.sendSimpleMail("房产激活邮件", content, email);
    }


    public boolean enable(String key) {

        //从Redis中获取key对应的值信息
        String value = redisTemplate.opsForValue().get(key);
        if (Strings.isNullOrEmpty(key)){
            throw new UserException(UserException.Type.USER_NOT_FOUND,"无效的key");
        }
        //更新数据库
        User user = new User();
        user.setEmail(value);
        user.setEnable(1);
        userMapper.update(user);
        return true;
    }
}

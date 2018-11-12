package com.house.userservice.controller;

import com.house.userservice.bean.User;
import com.house.userservice.config.respone.RestResponse;
import com.house.userservice.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/12
 * @ Description：
 * @ throws
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //-------------------查询---------------------

    @RequestMapping("getById")
    public RestResponse<User> getUserById(@Param("id") Long id){

        User user = userService.getUserById(id);
        return RestResponse.success(user);
    }
    @RequestMapping("getList")
    public  RestResponse<List<User>> getList(@RequestBody User user){

        List<User> userList = userService.getUserByUser(user);
        return RestResponse.success(userList);
    }
}

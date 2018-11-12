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

    //----------------------注册----------------------------------

    @RequestMapping("/add")
    public RestResponse<User> add(@RequestBody User user){

        userService.addUser(user);
        return RestResponse.success();
    }

    @RequestMapping("/enable")
    public RestResponse<User> enableUser(String key){

        userService.enable(key);
        return RestResponse.success();
    }
//------------------------登录/鉴权--------------------------

    @RequestMapping("/auth")
    public RestResponse<User> auth(@RequestBody User user){

        User finalUser = userService.auth(user.getEmail(),user.getPasswd());
        return RestResponse.success(finalUser);
    }

    /**
     * @ Author jmy
     * @ Description //TODO User
     * @ Date 2018/11/12
     * @ Param [token]
     * @ return com.house.userservice.config.respone.RestResponse<com.house.userservice.bean.User>
     * 根据token获取对象
     **/
    @RequestMapping("/get")
    public RestResponse<User> getUser(String token){

        User finalUser = userService.getLoginUserByToken(token);
        return RestResponse.success(finalUser);
    }

    @RequestMapping("/logOut")
    public RestResponse<User> logOut(String token){

        userService.invalidateUser(token);
        return RestResponse.success();
    }

}

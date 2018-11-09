package com.house.userservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/9
 * @ Description：
 * @ throws
 */
@RestController
public class TestTemplate {

    @RequestMapping("/getUser")
    public String test(Integer id){
        System.out.println(id+":从api-server传递过来的id值");
        return "test-user";
    }

}

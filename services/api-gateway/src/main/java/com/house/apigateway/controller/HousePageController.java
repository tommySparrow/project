package com.house.apigateway.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/14
 * @ Description：
 * @ throws
 */
@Controller
public class HousePageController {


    //访问域名跳转到首页
    @RequestMapping("")
    public String index(ModelMap modelMap){

        return "redirect:/index";
    }

    @RequestMapping("/index")
    public String index(){

        return "/homepage/index";
    }
}

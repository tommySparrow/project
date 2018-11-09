package com.house.apigateway.controller;

import com.house.apigateway.service.TestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/8
 * @ Description：
 * @ throws
 */
@RestController
public class TController {

    @Autowired
    private TestTemplateService testTemplateService;

    @RequestMapping("/getUser")
    public String test(Integer id){
        return testTemplateService.test(id);
    }
}

package com.house.apigateway.controller;

import com.house.apigateway.config.http.HttpClientProperties;
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
    private HttpClientProperties httpClientProperties;

    @RequestMapping("/test")
    public String test(){
        return httpClientProperties.getAgent();
    }
}

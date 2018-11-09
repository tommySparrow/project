package com.house.apigateway.service;

import com.house.apigateway.mapper.TestTemplateMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/9
 * @ Description：
 * @ throws
 */
@Service
public class TestTemplateService {

    @Autowired
    private TestTemplateMapper testTemplateMapper;

    public String test(Integer id){

        return testTemplateMapper.test(id);
    }
}

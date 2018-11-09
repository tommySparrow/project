package com.house.apigateway.mapper;

import com.house.apigateway.config.http.GenericRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

@Repository
public class TestTemplateMapper {

    @Autowired
    private GenericRest genericRest;

    public String test(Integer id){

        String url = "http://user"+"/getUser?id="+id;//调用服务名称方式
//        String url = "direct://http://127.0.0.1:8083"+"/getUser?id="+id;//使用IP+端口 直连方式
        ResponseEntity<String> stringResponseEntity = genericRest.get(url, new ParameterizedTypeReference<String>() {});
        return stringResponseEntity.getBody();
    }
}

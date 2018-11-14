package com.house.apigateway.mapper;

import com.house.apigateway.bean.User;
import com.house.apigateway.common.utils.Rests;
import com.house.apigateway.config.http.GenericRest;
import com.house.apigateway.config.respone.RestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/13
 * @ Description：
 * @ throws
 */
@Repository
public class UserMapper {

    @Autowired
    private GenericRest rest;

    @Value("${user.service.name}")
    private String userServiceName;

    /**
     * @ Author jmy
     * @ Description //TODO User
     * @ Date 2018/11/13
     * @ Param [user]
     * @ return java.util.List<com.house.apigateway.bean.User>
     * 获取用户
     **/

    public List<User> getUserList(User query) {
        ResponseEntity<RestResponse<List<User>>> resultEntity = rest.post("http://"+ userServiceName + "/user/getList",query, new ParameterizedTypeReference<RestResponse<List<User>>>() {});
        RestResponse<List<User>> restResponse  = resultEntity.getBody();
        if (restResponse.getCode() == 0) {
            return restResponse.getResult();
        }else {
            return null;
        }
    }


    public User addUser(User account) {
        String url = "http://" + userServiceName + "/user/add";
        ResponseEntity<RestResponse<User>> responseEntity = rest.post(url,account, new ParameterizedTypeReference<RestResponse<User>>() {});
        RestResponse<User> response = responseEntity.getBody();
        if (response.getCode() == 0) {
            return response.getResult();
        }{
            throw new IllegalStateException("Can not add user");
        }

    }

    public boolean enable(String key) {

        Rests.exc(() ->{
            String url = Rests.toUrl(userServiceName, "/user/enable?key=" + key);
            ResponseEntity<RestResponse<Object>> responseEntity =
                    rest.get(url, new ParameterizedTypeReference<RestResponse<Object>>() {});
            return responseEntity.getBody();
        });
        return true;
    }


    /**
     * @ Author jmy
     * @ Description 根据token获取user对象//TODO User
     * @ Date 2018/11/14
     * @ Param [value]
     * @ return com.house.apigateway.bean.User
     *
     **/
    public User getUserByToken(String token) {

        String url  = "http://"+userServiceName+"/user/get?token="+token;
        ResponseEntity<RestResponse<User>> responseEntity = rest.get(url, new ParameterizedTypeReference<RestResponse<User>>() {});
        RestResponse<User> response = responseEntity.getBody();
        if (response.getCode() != 0){
            return null;
        }
        return response.getResult();
    }
}

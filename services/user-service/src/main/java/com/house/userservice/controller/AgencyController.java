package com.house.userservice.controller;

import com.house.userservice.bean.Agency;
import com.house.userservice.bean.User;
import com.house.userservice.common.pages.PageParams;
import com.house.userservice.common.respone.ListResponse;
import com.house.userservice.config.respone.RestResponse;
import com.house.userservice.service.AgencyService;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/13
 * @ Description：
 * @ throws
 */
@RestController
@RequestMapping("/agency")
public class AgencyController {

    @Autowired
    private AgencyService agencyService;

    @RequestMapping("/add")//添加经纪机构
    public RestResponse<Agency> addAgency(@RequestBody Agency agency){

        agencyService.addAgency(agency);
        return RestResponse.success();
    }

    @RequestMapping("/list")//获取所有经纪机构
    public RestResponse<List<Agency>> getAgencys(){

        List<Agency> agencyList = agencyService.getAgencys();
        return RestResponse.success(agencyList);
    }

    @RequestMapping("/agentList")//分页显示经纪人
    public RestResponse<ListResponse<User>> agentList(Integer offset,Integer limit){

        //封装分页参数
        PageParams pageParams = new PageParams();
        pageParams.setOffset(offset);
        pageParams.setLimit(limit);
        //获取所有经纪人
        Pair<List<User>,Long> pair = agencyService.getAllAgent(pageParams);
        ListResponse<User> listResponse = ListResponse.build(pair.getKey(), pair.getValue());
        return RestResponse.success(listResponse);
    }
}

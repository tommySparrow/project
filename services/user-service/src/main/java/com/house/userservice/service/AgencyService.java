package com.house.userservice.service;

import com.house.userservice.bean.Agency;
import com.house.userservice.bean.User;
import com.house.userservice.common.pages.PageParams;
import com.house.userservice.mapper.AgencyMapper;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/13
 * @ Description：
 * @ throws
 */
@Service
public class AgencyService {

    @Autowired
    private AgencyMapper agencyMapper;

    @Value("${file.prefix}")
    private String imgPrefix;

    @Transactional(rollbackFor = Exception.class)
    public int addAgency(Agency agency) {

        return agencyMapper.insert(agency);
    }

    public List<Agency> getAgencys() {

        return agencyMapper.select(new Agency());
    }

    public Pair<List<User>, Long> getAllAgent(PageParams pageParams) {

        //获取经纪人列表
        List<User> userList = agencyMapper.selectAllAgent(new User(),pageParams);
        setImg(userList);//操作用户头像
        //查询总条数
        Long count = agencyMapper.selectAgentCount(new User());
        return ImmutablePair.of(userList, count);
    }

    private void setImg(List<User> userList) {

        userList.forEach(user -> {

            user.setAvatar(imgPrefix+user.getAvatar());
        });
    }
}

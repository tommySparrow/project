package com.house.userservice.service;

import com.house.userservice.bean.Agency;
import com.house.userservice.mapper.AgencyMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional(rollbackFor = Exception.class)
    public int addAgency(Agency agency) {

        return agencyMapper.insert(agency);
    }

    public List<Agency> getAgencys() {

        return agencyMapper.select(new Agency());
    }
}

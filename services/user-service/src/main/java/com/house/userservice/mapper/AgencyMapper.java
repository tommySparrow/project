package com.house.userservice.mapper;

import com.github.abel533.mapper.Mapper;
import com.house.userservice.bean.Agency;
import com.house.userservice.bean.User;
import com.house.userservice.common.pages.PageParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/13
 * @ Description：
 * @ throws
 */
public interface AgencyMapper extends Mapper<Agency> {

    List<User> selectAllAgent(@Param("user") User user, @Param("pageParams") PageParams pageParams);

    Long selectAgentCount(@Param("user") User user);
}

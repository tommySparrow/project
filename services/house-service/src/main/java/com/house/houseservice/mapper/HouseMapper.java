package com.house.houseservice.mapper;

import com.house.houseservice.bean.House;
import com.house.houseservice.common.LimitOffset;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/15
 * @ Description：
 * @ throws
 */
public interface HouseMapper {

    List<House> selectHouse(@Param("house") House query, @Param("pageParams") LimitOffset limitOffset);

    Long selectHouseCount(@Param("house") House query);

}

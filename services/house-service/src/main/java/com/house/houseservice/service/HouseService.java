package com.house.houseservice.service;

import com.google.common.collect.Lists;
import com.house.houseservice.bean.Community;
import com.house.houseservice.bean.House;
import com.house.houseservice.common.LimitOffset;
import com.house.houseservice.mapper.CommunityMapper;
import com.house.houseservice.mapper.HouseMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ Author     ：jmyang
 * @ Date       ：Created in 2018/11/15
 * @ Description：
 * @ throws
 */
@Service
public class HouseService {

    @Autowired
    private HouseMapper houseMapper;

    @Autowired
    private CommunityMapper communityMapper;

    @Value("${file.prefix}")
    private String imgPrefix;

    public Pair<List<House>, Long> queryHouse(House query, LimitOffset build) {
        List<House> houses = Lists.newArrayList();
        House houseQuery = query;
        if (StringUtils.isNoneBlank(query.getName())) {
            Community community = new Community();
            community.setName(query.getName());
//            List<Community> communities = houseMapper.selectCommunity(community);
            List<Community> communities = communityMapper.select(community);
            if (!communities.isEmpty()) {
                houseQuery = new House();
                houseQuery.setCommunityId(communities.get(0).getId());
            }
        }
        houses = queryAndSetImg(houseQuery, build);
        Long count = houseMapper.selectHouseCount(houseQuery);
        return ImmutablePair.of(houses, count);
    }
    public List<House> queryAndSetImg(House query, LimitOffset pageParams){
        List<House> houses =  houseMapper.selectHouse(query,pageParams);
        houses.forEach(h -> {
            h.setFirstImg(imgPrefix + h.getFirstImg());
            h.setImageList(h.getImageList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
            h.setFloorPlanList(h.getFloorPlanList().stream().map(img -> imgPrefix + img).collect(Collectors.toList()));
        });
        return houses;
    }
}

package com.tunnel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.tunnel.bean.Advertisingplace;
import com.tunnel.bean.AdvertisingplaceExample;
import com.tunnel.mapper.AdvertisingplaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdvertisingPlaceService {

    @Autowired
    AdvertisingplaceMapper advertisingPlaceMapper;

    public PageInfo getAdvertisingPlaceList(int pageNo, int pageSize, Short stop, String showtype, String advtype) {
        PageHelper.startPage(pageNo, pageSize);

        AdvertisingplaceExample example = new AdvertisingplaceExample();
        AdvertisingplaceExample.Criteria criteria = example.createCriteria();

        if (stop != null) {
            if (stop == 2) {
                criteria.andStopEqualTo((short) 0);
            } else {
                criteria.andStopEqualTo(stop);
            }
        }

        if (StringUtil.isNotEmpty(showtype)) {
            criteria.andShowtypeEqualTo(showtype);
        }

        if (StringUtil.isNotEmpty(advtype)) {
            criteria.andAdvtypeEqualTo(advtype);
        }

        List<Advertisingplace> advertisingPlaces = advertisingPlaceMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(advertisingPlaces);

        return pageInfo;
    }

    public Advertisingplace getDetailByPrimaryId(Long id) {
        return advertisingPlaceMapper.selectByPrimaryKey(id);
    }

    public void deleteAdvertisingPlaceById(Long advertisingPlaceId) {
        advertisingPlaceMapper.deleteByPrimaryKey(advertisingPlaceId);
    }

    public void addAdvertisingPlace(Advertisingplace advertisingPlace) {
        advertisingPlaceMapper.insertSelective(advertisingPlace);
    }

    public void updateAdvertisingPlace(Advertisingplace advertisingPlace) {
        advertisingPlaceMapper.updateByPrimaryKeySelective(advertisingPlace);
    }

    public Advertisingplace getAdvertisingPlaceByPosition(String position) {
        AdvertisingplaceExample example = new AdvertisingplaceExample();
        AdvertisingplaceExample.Criteria criteria = example.createCriteria();
        criteria.andPositionEqualTo(position);
        List<Advertisingplace> advertisingPlaces = advertisingPlaceMapper.selectByExample(example);
        if (advertisingPlaces.size() == 0) {
            return null;
        } else {
            return advertisingPlaces.get(0);
        }
    }

}

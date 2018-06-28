package com.tunnel.mapper;

import com.tunnel.bean.Advertisingplace;
import com.tunnel.bean.AdvertisingplaceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvertisingplaceMapper {
    int countByExample(AdvertisingplaceExample example);

    int deleteByExample(AdvertisingplaceExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Advertisingplace record);

    int insertSelective(Advertisingplace record);

    List<Advertisingplace> selectByExample(AdvertisingplaceExample example);

    Advertisingplace selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Advertisingplace record, @Param("example") AdvertisingplaceExample example);

    int updateByExample(@Param("record") Advertisingplace record, @Param("example") AdvertisingplaceExample example);

    int updateByPrimaryKeySelective(Advertisingplace record);

    int updateByPrimaryKey(Advertisingplace record);
}
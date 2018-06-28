package com.tunnel.mapper;

import com.tunnel.bean.floorData;
import com.tunnel.bean.floorDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface floorDataMapper {
    int countByExample(floorDataExample example);

    int deleteByExample(floorDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(floorData record);

    int insertSelective(floorData record);

    List<floorData> selectByExample(floorDataExample example);

    floorData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") floorData record, @Param("example") floorDataExample example);

    int updateByExample(@Param("record") floorData record, @Param("example") floorDataExample example);

    int updateByPrimaryKeySelective(floorData record);

    int updateByPrimaryKey(floorData record);
}
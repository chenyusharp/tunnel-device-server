package com.tunnel.mapper;

import com.tunnel.bean.CommonData;
import com.tunnel.bean.CommonDataExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommonDataMapper {
    int countByExample(CommonDataExample example);

    int deleteByExample(CommonDataExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommonData record);

    int insertSelective(CommonData record);

    List<CommonData> selectByExample(CommonDataExample example);

    CommonData selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommonData record, @Param("example") CommonDataExample example);

    int updateByExample(@Param("record") CommonData record, @Param("example") CommonDataExample example);

    int updateByPrimaryKeySelective(CommonData record);

    int updateByPrimaryKey(CommonData record);

    @Select("select * from commondata where name=#{name} order by id desc limit 1")
    CommonData getByName(@Param("name") String name);
}
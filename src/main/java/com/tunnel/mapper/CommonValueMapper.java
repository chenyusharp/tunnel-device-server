package com.tunnel.mapper;

import com.tunnel.bean.CommonValue;
import com.tunnel.bean.CommonValueExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CommonValueMapper {
    int countByExample(CommonValueExample example);

    int deleteByExample(CommonValueExample example);

    int deleteByPrimaryKey(Long id);

    int insert(CommonValue record);

    int insertSelective(CommonValue record);

    List<CommonValue> selectByExample(CommonValueExample example);

    CommonValue selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") CommonValue record, @Param("example") CommonValueExample example);

    int updateByExample(@Param("record") CommonValue record, @Param("example") CommonValueExample example);

    int updateByPrimaryKeySelective(CommonValue record);

    int updateByPrimaryKey(CommonValue record);
}
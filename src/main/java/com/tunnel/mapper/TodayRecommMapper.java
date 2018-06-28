package com.tunnel.mapper;

import com.tunnel.bean.TodayRecomm;
import com.tunnel.bean.TodayRecommExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TodayRecommMapper {
    int countByExample(TodayRecommExample example);

    int deleteByExample(TodayRecommExample example);

    int insert(TodayRecomm record);

    int insertSelective(TodayRecomm record);

    List<TodayRecomm> selectByExample(TodayRecommExample example);

    int updateByExampleSelective(@Param("record") TodayRecomm record, @Param("example") TodayRecommExample example);

    int updateByExample(@Param("record") TodayRecomm record, @Param("example") TodayRecommExample example);
}
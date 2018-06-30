package com.tunnel.mapper;

import com.tunnel.bean.TodayRecomm;
import com.tunnel.bean.TodayRecommExample;
import java.util.List;

import com.tunnel.bean.dto.TodayRecommProduct;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.parameters.P;

public interface TodayRecommMapper {
    int countByExample(TodayRecommExample example);

    int deleteByExample(TodayRecommExample example);

    int insert(TodayRecomm record);

    int insertSelective(TodayRecomm record);

    List<TodayRecomm> selectByExample(TodayRecommExample example);

    int updateByExampleSelective(@Param("record") TodayRecomm record, @Param("example") TodayRecommExample example);

    int updateByExample(@Param("record") TodayRecomm record, @Param("example") TodayRecommExample example);



    @Select("select T.*,P.productName,P.type,P.productAlias,P.unit,P.price,P.brand,P.model from todayrecomm T left join productinfo P on T.pdid=P.id where T.display=#{show} order by T.sort limit #{count}")
    List<TodayRecommProduct>  getTodayRecommProduct(@Param("count") int count,@Param("show") int show);
}
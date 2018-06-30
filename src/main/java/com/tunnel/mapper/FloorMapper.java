package com.tunnel.mapper;

import com.tunnel.bean.Floor;
import com.tunnel.bean.FloorExample;
import java.util.List;

import com.tunnel.bean.dto.FloorViewDto;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.cache.annotation.Cacheable;

public interface FloorMapper {
    int countByExample(FloorExample example);

    int deleteByExample(FloorExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Floor record);

    int insertSelective(Floor record);

    List<Floor> selectByExample(FloorExample example);

    Floor selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Floor record, @Param("example") FloorExample example);

    int updateByExample(@Param("record") Floor record, @Param("example") FloorExample example);

    int updateByPrimaryKeySelective(Floor record);

    int updateByPrimaryKey(Floor record);

    @Cacheable(value = "tunnel-floor",key="'getShowFloor'")
    @Select("select F.*,P.product_name as productName,P.product_alias as productAlias,P.brand,P.model,P.unit,P.price from floor F left join floordata FD on F.id=FD.floorid left join productinfo P on FD.dataid=P.id  where F.isshow=1 order by F.sort asc")
    List<FloorViewDto> getShowFloor();
}
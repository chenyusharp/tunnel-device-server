package com.tunnel.mapper;

import com.tunnel.bean.RecoverData;
import com.tunnel.bean.RecoverDataExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecoverDataMapper {
    int countByExample(RecoverDataExample example);

    int deleteByExample(RecoverDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(RecoverData record);

    int insertSelective(RecoverData record);

    List<RecoverData> selectByExample(RecoverDataExample example);

    RecoverData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") RecoverData record, @Param("example") RecoverDataExample example);

    int updateByExample(@Param("record") RecoverData record, @Param("example") RecoverDataExample example);

    int updateByPrimaryKeySelective(RecoverData record);

    int updateByPrimaryKey(RecoverData record);
}
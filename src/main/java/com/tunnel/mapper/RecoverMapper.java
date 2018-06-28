package com.tunnel.mapper;

import com.tunnel.bean.Recover;
import com.tunnel.bean.RecoverExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RecoverMapper {
    int countByExample(RecoverExample example);

    int deleteByExample(RecoverExample example);

    int deleteByPrimaryKey(Long id);

    int insert(Recover record);

    int insertSelective(Recover record);

    List<Recover> selectByExample(RecoverExample example);

    Recover selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") Recover record, @Param("example") RecoverExample example);

    int updateByExample(@Param("record") Recover record, @Param("example") RecoverExample example);

    int updateByPrimaryKeySelective(Recover record);

    int updateByPrimaryKey(Recover record);
}
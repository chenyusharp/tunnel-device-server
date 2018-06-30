package com.tunnel.mapper;

import com.tunnel.bean.SmsLog;
import com.tunnel.bean.SmsLogExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface SmsLogMapper {
    int countByExample(SmsLogExample example);

    int deleteByExample(SmsLogExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SmsLog record);

    int insertSelective(SmsLog record);

    List<SmsLog> selectByExample(SmsLogExample example);

    SmsLog selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SmsLog record, @Param("example") SmsLogExample example);

    int updateByExample(@Param("record") SmsLog record, @Param("example") SmsLogExample example);

    int updateByPrimaryKeySelective(SmsLog record);

    int updateByPrimaryKey(SmsLog record);
    @Select("select * from smslog where mobile=#{mobile} and type=#{type} order by id desc limit 1")
    SmsLog getLastLog(@Param("mobile") String mobile,@Param("type") short type);
}
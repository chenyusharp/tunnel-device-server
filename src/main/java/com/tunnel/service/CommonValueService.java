package com.tunnel.service;

import com.tunnel.bean.CommonValue;
import com.tunnel.bean.CommonValueExample;
import com.tunnel.mapper.CommonValueMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonValueService {

    @Autowired
    private CommonValueMapper commonValueMapper;

    public CommonValue getById(long id){
        return  commonValueMapper.selectByPrimaryKey(id);
    }

    public void updateByPrimaryKey(CommonValue commonValue){
        commonValueMapper.updateByPrimaryKey(commonValue);
    }

    public  void  insertSelective(CommonValue value){
        commonValueMapper.insertSelective(value);
    }

    public  void deleteById(long id){
        commonValueMapper.deleteByPrimaryKey(id);
    }

    public List<CommonValue> getValueById(long id){
        CommonValueExample example = new CommonValueExample();
        CommonValueExample.Criteria criteria = example.createCriteria();
        criteria.andCommonIdEqualTo(id);
        return  commonValueMapper.selectByExample(example);
    }

    public void deleteByCommonId(long id){
        CommonValueExample example = new CommonValueExample();
        CommonValueExample.Criteria criteria = example.createCriteria();
        criteria.andCommonIdEqualTo(id);
        commonValueMapper.deleteByExample(example);
    }
}

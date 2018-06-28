package com.tunnel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tunnel.bean.CommonData;
import com.tunnel.bean.CommonDataExample;
import com.tunnel.mapper.CommonDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CommonDataService {

    @Autowired
    private CommonDataMapper commonDataMapper;

    public CommonData getById(long id) {
        return commonDataMapper.selectByPrimaryKey(id);
    }

    public void deleteById(long id) {
        commonDataMapper.deleteByPrimaryKey(id);
    }

    public PageInfo listByPage(String name, int pageNo, int pageSize) {
        CommonDataExample example = new CommonDataExample();
        CommonDataExample.Criteria criteria = example.createCriteria();
        if (StringUtils.hasText(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        PageHelper.startPage(pageNo,pageSize);
        List<CommonData> list =  commonDataMapper.selectByExample(example);
        PageInfo pageInfo =  new PageInfo(list);
        return  pageInfo;
    }

    public CommonData getByName(String name){
        return  commonDataMapper.getByName(name);
    }

    public void updateByPrimaryKeySelective(CommonData data){
        commonDataMapper.updateByPrimaryKeySelective(data);
    }

    public  void  insertSelective(CommonData data){
        commonDataMapper.insertSelective(data);
    }
}

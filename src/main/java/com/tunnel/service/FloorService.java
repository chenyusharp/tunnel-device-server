package com.tunnel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.tunnel.bean.Floor;
import com.tunnel.bean.FloorExample;
import com.tunnel.bean.dto.FloorViewDto;
import com.tunnel.mapper.FloorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * create : wyh
 * date : 2017/12/28
 */

@Service
public class FloorService {

    @Autowired
    private FloorMapper floorMapper;


    @Autowired
    private  Gson gson;

    public void insertSelective(Floor floor){
        floorMapper.insertSelective(floor);
    }


    public  Floor getById(long id){
        return  floorMapper.selectByPrimaryKey(id);
    }


    public  void deleteById(long id){
        floorMapper.deleteByPrimaryKey(id);
    }

    public  void  updateByPrimaryKeySelective(Floor floor){
        floorMapper.updateByPrimaryKeySelective(floor);
    }


    public PageInfo listByPage(int pageNo,int pageSize){
        PageHelper.startPage(pageNo,pageSize);
        FloorExample example = new FloorExample();
        List<Floor> list = floorMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo(list);
        return  pageInfo;
    }




    public  List<FloorViewDto> getShowFloor(){
       return  floorMapper.getShowFloor();
    }




}

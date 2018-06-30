package com.tunnel.service;

import com.tunnel.bean.Floor;
import com.tunnel.bean.dto.CategoryViewDto;
import com.tunnel.common.constant.RedisCacheKey;
import com.tunnel.common.utils.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * create : wyh
 * date : 2018/2/27
 */
@Service
public class RedisCacheService {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IndexService indexService;


    /**
     * 添加首页楼层缓存
     * @return
     */
    public List addIndexFloor(){
        List list = indexService.getFloor();
        redisUtils.setList(RedisCacheKey.INDEX_FLOOR,list);
        return  list;
    }

    /**
     * 获取首页楼层缓存
     * @return
     */
    public List getIndexFloor(){
      return   redisUtils.getList(RedisCacheKey.INDEX_FLOOR,Floor.class);
    }


    /**
     * 添加展示类目缓存
     * @return
     */
    public  List<CategoryViewDto> addShowCate(){
        List list = indexService.getShowCate();
        redisUtils.setList(RedisCacheKey.SHOW_CATEGORY,list);
        return  list;
    }

    /**
     *获取展示类目缓存
     */
    public  List<CategoryViewDto> getShowCate(){
        return  redisUtils.getList(RedisCacheKey.SHOW_CATEGORY, CategoryViewDto.class);
    }





    public  void remove(String key){
        redisUtils.expire(key,0);
    }
}

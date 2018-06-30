package com.tunnel.service;

import com.tunnel.bean.dto.TodayRecommProduct;
import com.tunnel.mapper.TodayRecommMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * FrontService
 *
 * @author xiazy
 * @create 2018-06-30 14:01
 **/
@Service
public class FrontService {
    @Autowired
    private TodayRecommMapper todayRecommMapper;



    public List<TodayRecommProduct> getTodayRecommProduct(int count,int show){

        List<TodayRecommProduct> list=todayRecommMapper.getTodayRecommProduct(count,show);
        return list;
    }

}

package com.tunnel.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tunnel.bean.ProductInfo;
import com.tunnel.bean.ProductInfoQuery;
import com.tunnel.mapper.ProductInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ProductInfoService {

    @Autowired
    private ProductInfoMapper productInfoMapper;

    /**
     * 根据条件搜索产品信息(紧固件)
     *
     * @param pageNo
     * @param pageSize
     * @param info
     * @return
     */
    public PageInfo listProduct(int pageNo, int pageSize, ProductInfoQuery info) {
        PageHelper.startPage(pageNo, pageSize);
        List<ProductInfo> list = productInfoMapper.listProduct(info);

        return new PageInfo(list);
    }
}

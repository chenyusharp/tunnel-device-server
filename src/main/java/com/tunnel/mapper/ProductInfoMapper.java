package com.tunnel.mapper;

import com.tunnel.bean.ProductInfo;
import com.tunnel.bean.ProductInfoExample;
import com.tunnel.bean.ProductInfoQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductInfoMapper {
    int countByExample(ProductInfoExample example);

    int deleteByExample(ProductInfoExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    List<ProductInfo> selectByExample(ProductInfoExample example);

    ProductInfo selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByExample(@Param("record") ProductInfo record, @Param("example") ProductInfoExample example);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    @Select("<script>" +
            "select id,type,level1id,level2id,level3id,product_name,product_alias,title,brand,model,cross_border,unit,store_num,price,delivery,mold,pic,\"desc\",createtime,updatetime,downtime,state " +
            "from productinfo where 1=1  "+
            "<if test=\"info.productname !=null and info.productname!= ''\">and product_name like '%${info.productname}%' </if>" +
            "<if test=\"info.type &gt; 0\">and type = #{info.type} </if>" +
            "<if test=\"info.mold &gt; 0\">and mold = #{info.mold} </if>" +
            "<if test=\"info.brand != null and info.brand != ''\">and brand = #{info.brand} </if>" +
            "<if test=\"info.model != null and info.model != ''\">and model = #{info.model} </if>" +
            "<if test=\"info.crossborder &gt; -1\">and crossborder = #{info.crossborder} </if>" +
            "<if test=\"info.state &gt; 0\">and state = #{info.state} </if>" +
            "</script>")
    List<ProductInfo> listProduct(@Param("info") ProductInfoQuery info);
}
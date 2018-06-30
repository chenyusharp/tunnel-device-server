package com.tunnel.bean.dto;

import com.tunnel.bean.ProductInfo;
import com.tunnel.bean.TodayRecomm;

import java.math.BigDecimal;

/**
 * 今日推荐产品
 *
 * @author xiazy
 * @create 2018-06-30 14:09
 **/
public class TodayRecommProduct extends TodayRecomm {
    private Short type;

    private String productName;

    private String productAlias;

    private String brand;

    private String model;

    private Short crossBorder;

    private String unit;

    private BigDecimal storeNum;

    private BigDecimal price;

    private String delivery;

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAlias() {
        return productAlias;
    }

    public void setProductAlias(String productAlias) {
        this.productAlias = productAlias;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Short getCrossBorder() {
        return crossBorder;
    }

    public void setCrossBorder(Short crossBorder) {
        this.crossBorder = crossBorder;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getStoreNum() {
        return storeNum;
    }

    public void setStoreNum(BigDecimal storeNum) {
        this.storeNum = storeNum;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDelivery() {
        return delivery;
    }

    public void setDelivery(String delivery) {
        this.delivery = delivery;
    }
}

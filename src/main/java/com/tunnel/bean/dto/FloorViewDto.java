package com.tunnel.bean.dto;

import com.tunnel.bean.Floor;

import java.math.BigDecimal;

/**
 * FloorViewDto
 *
 * @author xiazy
 * @create 2018-06-30 16:27
 **/
public class FloorViewDto extends Floor{
    private String productName;

    private String productAlias;

    private String brand;

    private String model;


    private String unit;


    private BigDecimal price;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}

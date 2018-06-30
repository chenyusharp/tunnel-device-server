package com.tunnel.bean;

import io.swagger.annotations.ApiModelProperty;

public class ProductInfoQuery {
    @ApiModelProperty(notes = "商品名")
    private String productname;

    @ApiModelProperty(notes = "类别 1=隧道设备")
    private short type;

    @ApiModelProperty(notes = "分类 1=全新 2=二手")
    private short mold;

    @ApiModelProperty(notes = "品牌")
    private String brand;

    @ApiModelProperty(notes = "型号")
    private String model;

    @ApiModelProperty(notes = "是否跨境货源 0=否，1=是")
    private short crossborder;

    @ApiModelProperty(notes = "状态 1=上架 2=下架 3=待发布 4=删除")
    private short state;

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public short getMold() {
        return mold;
    }

    public void setMold(short mold) {
        this.mold = mold;
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

    public short getCrossborder() {
        return crossborder;
    }

    public void setCrossborder(short crossborder) {
        this.crossborder = crossborder;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }
}

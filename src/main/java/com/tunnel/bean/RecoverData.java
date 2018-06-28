package com.tunnel.bean;

public class RecoverData {
    private Integer id;

    private Integer recoverId;

    private String name;

    private String mold;

    private String price;

    private Object pic;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRecoverId() {
        return recoverId;
    }

    public void setRecoverId(Integer recoverId) {
        this.recoverId = recoverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getMold() {
        return mold;
    }

    public void setMold(String mold) {
        this.mold = mold == null ? null : mold.trim();
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price == null ? null : price.trim();
    }

    public Object getPic() {
        return pic;
    }

    public void setPic(Object pic) {
        this.pic = pic;
    }
}
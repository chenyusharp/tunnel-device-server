package com.tunnel.bean;

import java.util.Date;

public class Advertisingplace {
    private Long id;

    private String position;

    private String describe;

    private String advtype;

    private String showtype;

    private String gettag;

    private Short stop;

    private Float weight;

    private Float height;

    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getAdvtype() {
        return advtype;
    }

    public void setAdvtype(String advtype) {
        this.advtype = advtype == null ? null : advtype.trim();
    }

    public String getShowtype() {
        return showtype;
    }

    public void setShowtype(String showtype) {
        this.showtype = showtype == null ? null : showtype.trim();
    }

    public String getGettag() {
        return gettag;
    }

    public void setGettag(String gettag) {
        this.gettag = gettag == null ? null : gettag.trim();
    }

    public Short getStop() {
        return stop;
    }

    public void setStop(Short stop) {
        this.stop = stop;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
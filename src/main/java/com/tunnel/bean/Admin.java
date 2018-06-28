package com.tunnel.bean;

import java.io.Serializable;

public class Admin implements Serializable {
    private Long id;

    private String userName;

    private String password;

    private String name;

    private String telphone;

    private Object privilege;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone == null ? null : telphone.trim();
    }

    public Object getPrivilege() {
        return privilege;
    }

    public void setPrivilege(Object privilege) {
        this.privilege = privilege;
    }
}
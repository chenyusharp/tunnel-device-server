package com.tunnel.common.constant;

/**
 *验证码类型
 * @author xiazy
 * @date  2018/6/27 17:49
 * @return
 */
public enum SmsType {
    TYPE1(1,"注册"),
    TYPE2(2,"验证");
    private Short type;
    private String typeName;

    SmsType(int type, String typeName) {
        this.type =(short)type;
        this.typeName = typeName;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}

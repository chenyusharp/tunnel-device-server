package com.tunnel.common.bean;

import io.swagger.annotations.ApiModelProperty;

import java.util.HashMap;
import java.util.Map;

public class BasicRet {
    private static final String key_result = "result";
    private static final String key_message = "message";
    public static final int ERR = 0;
    public static final int SUCCESS = 1;
    public static final int TOKEN_ERR = 2;
    @ApiModelProperty(
            notes = "返回码: 0-err, 1-success, 2-token_err"
    )
    protected int result;
    protected String message;

    public BasicRet() {
    }

    public BasicRet(int result) {
        this.result = result;
    }

    public BasicRet(int result, String message) {
        this(result);
        this.message = message;
    }

    public Map<String, Object> map() {
        Map<String, Object> map = new HashMap();
        map.put("result", this.result);
        map.put("message", this.message);
        return map;
    }

    public int getResult() {
        return this.result;
    }

    public BasicRet setResult(int result) {
        this.result = result;
        return this;
    }

    public String getMessage() {
        return this.message;
    }

    public BasicRet setMessage(String message) {
        this.message = message;
        return this;
    }
}
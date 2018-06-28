package com.tunnel.config;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * create : wyh
 * date : 2018/6/27
 */

@Configuration
@ConfigurationProperties("mod.ali-dayu")
public class AliDayuConfig {

    private String AccessKeyID;

    private  String AccessKeySecret;

    //产品名称:云通信短信API产品,开发者无需替换
    static final String product = "Dysmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dysmsapi.aliyuncs.com";

    @Bean
    public IAcsClient acsClient() throws ClientException {
        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", this.AccessKeyID, this.AccessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", this.product, this.domain);
        IAcsClient acsClient = new DefaultAcsClient(profile);
        return acsClient;
    }




    public String getAccessKeyID() {
        return AccessKeyID;
    }

    public void setAccessKeyID(String accessKeyID) {
        AccessKeyID = accessKeyID;
    }

    public String getAccessKeySecret() {
        return AccessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        AccessKeySecret = accessKeySecret;
    }
}

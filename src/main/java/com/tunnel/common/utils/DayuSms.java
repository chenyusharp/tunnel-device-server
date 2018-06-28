package com.tunnel.common.utils;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * create : wyh
 * date : 2018/6/27
 */

@Component
public class DayuSms {

    Logger logger = LoggerFactory.getLogger(DayuSms.class);

    @Autowired
    private IAcsClient acsClient;

    public boolean sendSms(String phone,String templateCode,Map<String,Object> params) {
        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("阿里云短信测试专用");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode(templateCode);
        //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
        //request.setTemplateParam("{\"code\":\"123\"}");
        request.setTemplateParam(GsonUtils.toJson(params));

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        //request.setSmsUpExtendCode("90997");

        //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        //request.setOutId("yourOutId");

        //hint 此处可能会抛出异常，注意catch
        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = acsClient.getAcsResponse(request);
            if(sendSmsResponse.getCode().equalsIgnoreCase("OK")){
                return  true;
            }
        } catch (ClientException e) {
            logger.error("短信发送失败",e);
            return false;
        }

        return  false;
    }

}

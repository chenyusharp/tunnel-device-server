package com.tunnel.service;

import com.tunnel.bean.SmsLog;
import com.tunnel.common.constant.AppConstant;
import com.tunnel.common.utils.CommonUtils;
import com.tunnel.common.utils.DateUtils;
import com.tunnel.common.utils.DayuSms;
import com.tunnel.mapper.SmsLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * create : wyh
 * date : 2017/10/28
 */
@Service
public class MobileService {

    @Autowired
    private SmsLogMapper smsLogMapper;
    @Autowired
    private DayuSms dayuSms;


    public boolean sendMobileCode(HttpServletRequest request, String mobile,short type) {

        //生成验证码
        String verifyCode = CommonUtils.genVerificationCode(4);
        String content = "尊敬的用户，您正在进行手机验证操作，您的验证码为:${verifyCode}验证码有效期为5分钟";
        Map<String,Object> params=new HashMap<>();
        params.put("verifyCode",verifyCode);
        //  调用手机发送验证码接口
        boolean b = dayuSms.sendSms(mobile,content,params);
        String code = null;

        if(mobile.equals(AppConstant.MOCK_MOBILE)){
            code = AppConstant.MOCK_VERIFY_CODE;
        }else{
//            code = dayuSms.send(mobile);
            code=verifyCode;
        }

        if(code==null){
            return  false;
        }

        request.getSession(true).setAttribute("mobileCode",code);
        SmsLog smsLog =  new SmsLog();
        smsLog.setMobile(mobile);
        smsLog.setType(type);
        smsLog.setCode(code);
        smsLog.setMessage("");
        saveSmsLog(smsLog);
        return true;
    }

    public void saveSmsLog(SmsLog smsLog) {
        smsLog.setSendTime(new Date());
        smsLogMapper.insert(smsLog);
    }


    public  SmsLog getLastLog(String mobile,short type,int minutes){
       SmsLog smsLog =  smsLogMapper.getLastLog(mobile,type);
       if(smsLog != null && DateUtils.diffSeconds(smsLog.getSendTime(),new Date())<=minutes*60){
           return  smsLog;
       }
       return  null;
    }

    /**
     * 验证原手机号码
     */
    public boolean checkMobileCode(String mobile,String mobilecode,short type){
        boolean bool=false;
        SmsLog smsLog=smsLogMapper.getLastLog(mobile,type);

        if(smsLog==null){
            return  false;
        }

        if (!mobilecode.equals(smsLog.getCode())){
            return false;
        }else {
            return true;
        }
    }

    }

package com.tunnel.config;

import com.tunnel.common.bean.BasicRet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;


/**
 * create : wyh
 * date : 2017/11/2
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BasicRet defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        BasicRet basicRet =  new BasicRet(BasicRet.ERR);
        basicRet.setMessage(e.getMessage());

        e.printStackTrace();
        logger.error(e.getMessage());

        return  basicRet;
    }



    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseBody
    public BasicRet authorizationErrorHandler(HttpServletRequest req, Exception e) throws Exception {
        BasicRet basicRet =  new BasicRet(BasicRet.TOKEN_ERR);
        basicRet.setMessage("没有权限访问");
        return  basicRet;
    }

}

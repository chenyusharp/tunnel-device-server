package com.tunnel.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * create : wyh
 * date : 2017/11/3
 */
public class DefaultIntercep extends HandlerInterceptorAdapter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public DefaultIntercep() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(" -------- request: \n");
        sb.append(request.getRequestURI()).append(" ").append(request.getHeader("X-Real-IP")).append(" X-Forwarded-Proto:").append(request.getHeader("X-Forwarded-Proto")).append("\n");
        sb.append("sessionid: ").append(request.getSession().getId()).append(" \n");
        Enumeration e = request.getParameterNames();

        while(e.hasMoreElements()) {
            String param = (String)e.nextElement();
            sb.append(param).append(":").append(request.getParameter(param)).append("\n");
        }

        this.logger.info(sb.toString());
        return true;
    }

}

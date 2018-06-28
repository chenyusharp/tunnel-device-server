package com.tunnel.interceptor;

import com.tunnel.bean.Admin;
import com.tunnel.common.bean.BasicRet;
import com.tunnel.common.constant.AppConstant;
import com.tunnel.common.utils.GsonUtils;
import com.tunnel.config.security.AdminSecurityConfig;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * create : wyh
 * date : 2017/11/3
 */
public class AdminInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Admin admin = (Admin) session.getAttribute(AppConstant.ADMIN_SESSION_NAME);
        if(admin==null){
            response.getWriter().print(GsonUtils.toJson(new BasicRet(BasicRet.TOKEN_ERR,"token error")));
            return false;
        }else{
            AdminSecurityConfig.authMemberStore(admin);
            return true;
        }
    }
}

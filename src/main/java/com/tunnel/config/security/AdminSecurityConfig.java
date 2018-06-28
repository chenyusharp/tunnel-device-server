package com.tunnel.config.security;

import com.tunnel.bean.Admin;
import com.tunnel.bean.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashSet;
import java.util.Set;

/**
 * create : wyh
 */


public class AdminSecurityConfig extends SecurityConfig {


    public static void authMemberStore(Admin admin){
        /* 每次请求这里都要执行 每次都是新的 TODO */
        /* security 作用于当前线程 */
        Set<GrantedAuthority> authorities = new HashSet<>();

        String[] roles = (String[]) admin.getPrivilege();

        if(roles != null) {
            for (String p : roles) {
                authorities.add(new SimpleGrantedAuthority(p));
            }
        }
        Authentication req = new UsernamePasswordAuthenticationToken(admin.getId(), admin.getPassword(),authorities);
        SecurityContextHolder.getContext().setAuthentication(req);
    }


}

package com.tunnel.config.security;

import com.tunnel.bean.Admin;
import com.tunnel.mapper.AdminMapper;
import com.tunnel.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * create : wyh
 * date : 2018/6/27
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminMapper.getByUserName(username);
        if(admin == null){
            return  null;
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        if(admin.getPrivilege() != null){
            for(String privilege : (String[]) admin.getPrivilege()){
                authorities.add(new SimpleGrantedAuthority(privilege));
            }
        }

        User user = new User(username,admin.getPassword(),authorities);

        return user;
    }
}

package com.tunnel.config;


import com.tunnel.interceptor.DefaultIntercep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * create : wyh
 * date : 2017/11/3
 */


@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(defaultIntercep()).addPathPatterns("/**");

    }

    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(104857600);
        multipartResolver.setMaxInMemorySize(4096);
        // lazy加载，使用时才接收数据
        multipartResolver.setResolveLazily(true);
        return multipartResolver;
    }


    @Bean
    DefaultIntercep defaultIntercep(){
        return new DefaultIntercep();
    }

}

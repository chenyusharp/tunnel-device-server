package com.tunnel.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
// 因为是有@Bean的@Configuration 采用扫描方式
@Import({
        DataSourcePgsql1Conf.class,
        KaptchaConfig.class
})
// for mybatis-spring
@MapperScan({
        "com.tunnel"
})
public class RestCoreConfig {

}

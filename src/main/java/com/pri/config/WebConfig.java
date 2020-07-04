package com.pri.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * className: WebConfig <BR>
 * description: <BR>
 * remark: 由于Spring boot starter自动装配机制，这里无需使用@EnableWebMvc与@ComponentScan<BR>
 * author: ChenQi <BR>
 * createDate: 2020-07-04 21:29 <BR>
 */
@Configuration//就相当于springmvc.xml文件
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // 默认Url根路径跳转到/login，此url为spring security提供  1024
        registry.addViewController("/").setViewName("redirect:/login");
        /*// 自定义登录页面 ChenQi
        registry.addViewController("/").setViewName("redirect:/login-view");
        registry.addViewController("/login-view").setViewName("login");*/
    }
}

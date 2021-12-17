package com.tonpower.springbootweb.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.tonpower.springbootweb.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @description: MVC组件主配置类
 * @author: li377650260
 * @date: 2021/10/12 21:21
 */
@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Autowired
    LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/test")
        .addPathPatterns("/api/comments/create/change")
        .addPathPatterns("/api/articles/publish");
    }


    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**").allowedOrigins("http://localhost:80");
        registry.addMapping("/**").allowedOrigins("http://localhost:63343");
        registry.addMapping("/**").allowedOrigins("http://127.0.0.1:80");
        registry.addMapping("/**").allowedOrigins("http://127.0.0.1:8080");
        registry.addMapping("/**").allowedOrigins("http://101.37.163.78:8080");
        registry.addMapping("/**").allowedOrigins("http://101.37.163.78:80");
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");
    }
}

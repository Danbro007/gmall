package com.danbro.gmall.web.utils.config;

import com.danbro.gmall.web.utils.interceptor.AuthInterceptor;
import com.danbro.gmall.web.utils.interceptor.ResponseResultInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Danrbo
 * @date 2019/11/6 14:06
 * description
 **/

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    @Autowired
    ResponseResultInterceptor responseResultInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**");
//        registry.addInterceptor(responseResultInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**");
    }
}

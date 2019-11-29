package com.danbro.gmall.common.utils.config;


import com.danbro.gmall.common.utils.interceptor.AuthInterceptor;
import com.danbro.gmall.common.utils.interceptor.ResponseResultInterceptor;
import com.danbro.gmall.common.utils.processor.CustomServletModelAttributeMethodProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Danrbo
 * @date 2019/11/6 14:06
 * description
 **/

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private AuthInterceptor authInterceptor;
    private ResponseResultInterceptor responseResultInterceptor;

    public WebConfig(AuthInterceptor authInterceptor, ResponseResultInterceptor responseResultInterceptor) {
        this.authInterceptor = authInterceptor;
        this.responseResultInterceptor = responseResultInterceptor;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**");
        registry.addInterceptor(responseResultInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new CustomServletModelAttributeMethodProcessor(true));
    }
}

package com.hdu.hdufpga.config;

import com.hdu.hdufpga.interceptor.VisitCountInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Resource
    private VisitCountInterceptor visitCountInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(visitCountInterceptor).addPathPatterns("/**");
    }
}

package com.hdu.hdufpga.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
@Slf4j
public class CorsFilter implements Filter {

    static final String OPTIONS = "OPTIONS";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 允许指定域访问跨域资源
        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许所有请求方式
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        // 有效时间
        response.setHeader("Access-Control-Max-Age", "3600");
        // 允许的header参数
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,satoken");
        // 如果是预检请求，直接返回
        if (OPTIONS.equals(request.getMethod())) {
            log.info("=======================浏览器发来了OPTIONS预检请求==========");
            response.getWriter().print("");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

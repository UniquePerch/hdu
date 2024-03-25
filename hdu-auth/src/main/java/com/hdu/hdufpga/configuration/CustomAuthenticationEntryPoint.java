package com.hdu.hdufpga.configuration;

import cn.hutool.json.JSONUtil;
import com.hdu.hdufpga.entity.Result;
import org.apache.commons.lang.CharEncoding;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding(CharEncoding.UTF_8);
        response.getWriter().write(JSONUtil.toJsonStr(Result.error(401,"用户未登录")));
    }
}

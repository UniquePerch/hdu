package com.hdu.hdufpga.interceptor;

import lombok.NonNull;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class VisitCountInterceptor implements HandlerInterceptor {
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull Object handler) {
        if(stringRedisTemplate.opsForValue().get(request.getSession().getId()) != null) {
            stringRedisTemplate.expire(request.getSession().getId(), 30, TimeUnit.MINUTES);
        } else {
            stringRedisTemplate.opsForValue().set(request.getSession().getId(), "1",30, TimeUnit.MINUTES);
        }
        return true;
    }
}

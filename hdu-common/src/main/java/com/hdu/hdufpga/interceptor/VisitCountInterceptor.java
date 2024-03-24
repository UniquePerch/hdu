package com.hdu.hdufpga.interceptor;

import com.hdu.hdufpga.util.RedisUtil;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class VisitCountInterceptor implements HandlerInterceptor {
    @Resource
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response,@NonNull Object handler) {
        if(redisUtil.get(request.getSession().getId()) != null) {
            redisUtil.expire(request.getSession().getId(), 30, TimeUnit.MINUTES);
        } else {
            redisUtil.set(request.getSession().getId(), "1",30, TimeUnit.MINUTES);
        }
        return true;
    }
}

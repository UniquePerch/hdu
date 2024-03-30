package com.hdu.hdufpga.interceptor;

import com.hdu.hdufpga.util.RedisUtil;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

import static com.hdu.hdufpga.entity.constant.RedisConstant.REDIS_SESSION_PREFIX;

@Component
public class VisitCountInterceptor implements HandlerInterceptor {
    @Resource
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {
        if (redisUtil.get(REDIS_SESSION_PREFIX + request.getSession().getId()) != null) {
            redisUtil.expire(REDIS_SESSION_PREFIX + request.getSession().getId(), 30, TimeUnit.MINUTES);
        } else {
            redisUtil.set(REDIS_SESSION_PREFIX + request.getSession().getId(), "1", 30, TimeUnit.MINUTES);
        }
        return true;
    }
}

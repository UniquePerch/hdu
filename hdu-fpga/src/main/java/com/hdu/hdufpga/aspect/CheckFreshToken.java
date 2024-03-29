package com.hdu.hdufpga.aspect;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.util.RedisUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class CheckFreshToken {
    @Resource
    HttpServletRequest request;

    @Resource
    RedisUtil redisUtil;

    @Around("@annotation(com.hdu.hdufpga.annotation.CheckAndRefreshToken)")
    public Object refreshUserTTL(ProceedingJoinPoint joinPoint) throws Throwable {
        String token = request.getHeader("token");
        if (redisUtil.getExpire(RedisConstant.REDIS_TTL_PREFIX + token, TimeUnit.SECONDS) > 0) {
            redisUtil.expire(RedisConstant.REDIS_TTL_PREFIX + token, RedisConstant.REDIS_TTL_LIMIT, TimeUnit.SECONDS);
            return joinPoint.proceed();
        } else {
            return Result.error(401, "token已经过期，资源已经释放");
        }
    }
}

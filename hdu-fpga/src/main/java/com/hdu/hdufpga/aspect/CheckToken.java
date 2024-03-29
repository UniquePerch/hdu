package com.hdu.hdufpga.aspect;

import cn.hutool.core.lang.Validator;
import com.hdu.hdufpga.entity.Result;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class CheckToken {
    @Resource
    HttpServletRequest request;

    @Around("@annotation(com.hdu.hdufpga.annotation.CheckToken)")
    public Object checkToken(ProceedingJoinPoint joinPoint) throws Throwable {
        String token = request.getHeader("token");
        if (Validator.isNull(token)) {
            return Result.error("token为空");
        }
        return joinPoint.proceed();
    }
}

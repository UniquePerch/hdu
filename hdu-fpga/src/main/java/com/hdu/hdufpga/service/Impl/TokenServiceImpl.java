package com.hdu.hdufpga.service.Impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.vo.UserVO;
import com.hdu.hdufpga.service.TokenService;
import com.hdu.hdufpga.util.RedisUtil;
import com.hdu.hdufpga.utils.ParamUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    RedisUtil redisUtil;

    @Override
    public Result generateToken(UserVO userVO) {
        if (ParamUtil.CheckUserInfoLegal(userVO)) {
            String salt = IdUtil.simpleUUID();
            String token = ParamUtil.generateUserToken(userVO, salt);
            if (Validator.isNull(token)) {
                return Result.error("身份信息有误");
            }
            redisUtil.set(RedisConstant.REDIS_TTL_PREFIX + token, true, RedisConstant.REDIS_TTL_LIMIT, TimeUnit.SECONDS);
            return Result.ok(token);
        }
        return Result.error("身份信息有误");
    }
}

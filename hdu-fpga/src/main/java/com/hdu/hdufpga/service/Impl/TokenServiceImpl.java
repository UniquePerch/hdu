package com.hdu.hdufpga.service.Impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;
import com.hdu.hdufpga.entity.vo.UserVO;
import com.hdu.hdufpga.exception.IdentifyException;
import com.hdu.hdufpga.exception.NullTokenException;
import com.hdu.hdufpga.exception.TokenExpiredException;
import com.hdu.hdufpga.service.TokenService;
import com.hdu.hdufpga.service.WaitingService;
import com.hdu.hdufpga.util.RedisUtil;
import com.hdu.hdufpga.utils.ParamUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {
    @Resource
    RedisUtil redisUtil;

    @Resource
    WaitingService waitingService;

    @Override
    public String generateToken(UserVO userVO) throws IdentifyException {
        if (ParamUtil.CheckUserInfoLegal(userVO)) { // 检查传递的userVO参数是否为空
            String salt = IdUtil.simpleUUID(); // 随机生成一个uuid
            String token = ParamUtil.generateUserToken(userVO, salt); //生成token
            if (Validator.isNull(token)) {
                throw new IdentifyException("身份信息有误");
            }
            redisUtil.set(RedisConstant.REDIS_TTL_PREFIX + token, true, RedisConstant.REDIS_TTL_LIMIT, TimeUnit.SECONDS);
            return token;
        }
        throw new IdentifyException("身份信息有误");
    }

    @Override
    public UserConnectionVO reload(String token) throws Exception {
        redisUtil.set(RedisConstant.REDIS_TTL_PREFIX + token, true, RedisConstant.REDIS_TTL_LIMIT, TimeUnit.SECONDS);
        return waitingService.userInQueue(token);
    }

    @Override
    public Boolean checkToken(String token) throws NullTokenException, TokenExpiredException {
        if (Validator.isNull(token)) {
            throw new NullTokenException("token为空");
        }
        Boolean res = redisUtil.hasKey(RedisConstant.REDIS_TTL_PREFIX + token);
        if (res) {
            return true;
        }
        throw new TokenExpiredException("token已过期");
    }
}

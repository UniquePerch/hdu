package com.hdu.hdufpga.service.Impl;

import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.service.WaitingService;
import com.hdu.hdufpga.util.RedisUtil;
import com.hdu.hdufpga.util.RedissionLockUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WaitingServiceImpl implements WaitingService {
    @Resource
    RedisUtil redisUtil;

    @Resource
    RedissionLockUtil lockUtil;

    @Value("${wait-queue.name}")
    String queueName;

    @Override
    public Long getRankInQueue(String token) {
        return redisUtil.getZSetRank(queueName, token);
    }

    @Override
    public Boolean userInQueue(String token) {
        lockUtil.lock(queueName);
        Boolean res = redisUtil.addInZSet(queueName, token);
        redisUtil.expire(RedisConstant.REDIS_TTL_PREFIX + token, -1, TimeUnit.SECONDS);
        lockUtil.unlock(queueName);
        return res;
    }
}

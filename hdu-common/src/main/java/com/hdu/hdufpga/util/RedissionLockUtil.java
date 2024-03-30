package com.hdu.hdufpga.util;

import com.hdu.hdufpga.entity.constant.RedisConstant;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class RedissionLockUtil {
    @Resource
    private RedissonClient redissonClient;

    public void lock(String lockName) {
        if (redissonClient == null) {
            log.error("redission Client is null");
            return;
        }

        try {
            RLock rLock = redissonClient.getFairLock(RedisConstant.REDIS_LOCK_PREFIX + lockName);
            rLock.lock(15, TimeUnit.SECONDS);
            log.info("Thread [{}] DistributedRedisLock lock [{}] success", Thread.currentThread().getName(), lockName);
            // 加锁成功
        } catch (Exception e) {
            log.error("DistributedRedisLock lock [{}] Exception:", lockName, e);
        }
    }

    public void unlock(String lockName) {
        if (redissonClient == null) {
            log.error("redission Client is null");
            return;
        }

        try {
            RLock lock = redissonClient.getFairLock(RedisConstant.REDIS_LOCK_PREFIX + lockName);
            lock.unlock();
            log.info("Thread [{}] DistributedRedisLock unlock [{}] success", Thread.currentThread().getName(), lockName);
            // 释放锁成功
        } catch (Exception e) {
            log.error("DistributedRedisLock unlock [{}] Exception:", lockName, e);
        }
    }
}

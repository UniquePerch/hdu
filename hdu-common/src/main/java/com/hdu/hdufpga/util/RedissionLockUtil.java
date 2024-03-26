package com.hdu.hdufpga.util;

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

    public boolean lock(String lockName) {
        if (redissonClient == null) {
            log.error("redission Client is null");
            return false;
        }

        try {
            RLock rLock = redissonClient.getFairLock(lockName);
            rLock.lock(15, TimeUnit.SECONDS);
            log.info("Thread [{}] DistributedRedisLock lock [{}] success", Thread.currentThread().getName(), lockName);
            // 加锁成功
            return true;
        } catch (Exception e) {
            log.error("DistributedRedisLock lock [{}] Exception:", lockName, e);
            return false;
        }
    }

    public boolean unlock(String lockName) {
        if (redissonClient == null) {
            log.error("redission Client is null");
            return false;
        }

        try {
            RLock lock = redissonClient.getFairLock(lockName);
            lock.unlock();
            log.info("Thread [{}] DistributedRedisLock unlock [{}] success", Thread.currentThread().getName(), lockName);
            // 释放锁成功
            return true;
        } catch (Exception e) {
            log.error("DistributedRedisLock unlock [{}] Exception:", lockName, e);
            return false;
        }
    }
}

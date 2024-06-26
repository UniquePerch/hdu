package com.hdu.hdufpga;

import com.hdu.hdufpga.util.RedisUtil;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
public class TestZset {
    @Resource
    RedisUtil redisUtil;

    @Value("${wait-queue.name}")
    String queueName;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Resource
    RedissonClient redissonClient;

    @Test
    void testZrank() {
        System.out.println();
        System.out.println(redisUtil.getZSetRank(queueName, "tail"));
    }

    @Test
    void testRedlock() {
        RLock rlock = redissonClient.getLock("1");
        rlock.lock();
        rlock.unlock();

    }

    @Test
    @Transactional
    void testTransaction() {
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.multi();
        redisTemplate.exec();
    }

    @Value("${wait-queue.name}")
    String getQueueName;

    @Test
    void testGet() {
        redisUtil.putHashValue("1", "1", "1");
    }

}

package com.hdu.hdufpga.util;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public void expire(String key, long time, TimeUnit timeUnit) {
        try {
            redisTemplate.expire(key, time, timeUnit);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public Long getExpire(String key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public Boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public Boolean set(String key, Object value, long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, timeUnit);
                return true;
            } else {
                throw new IllegalArgumentException("过期时间为负值");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void del(String... keys) {
        if (keys != null && keys.length > 0) {
            if (keys.length == 1) {
                redisTemplate.delete(keys[0]);
            } else {
                redisTemplate.delete(CollectionUtil.newArrayList(keys));
            }
        }
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }
}

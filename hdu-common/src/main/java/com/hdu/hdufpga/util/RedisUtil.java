package com.hdu.hdufpga.util;

import cn.hutool.core.collection.CollectionUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
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

    public void multi() {
        redisTemplate.multi();
    }

    public void exec() {
        redisTemplate.exec();
    }

    public void discard() {
        redisTemplate.discard();
    }

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Long getZSetRank(String k, String o) {
        return redisTemplate.opsForZSet().rank(k, o);
    }

    public void addInZSet(String k, String v) {
        redisTemplate.opsForZSet().add(k, v, getZSetSize(k));
    }

    public void removeInZSet(String k, String v) {
        redisTemplate.opsForZSet().remove(k, v);
    }

    public Long getZSetSize(String k) {
        return redisTemplate.opsForZSet().zCard(k);
    }

    public Object getHashValue(String k1, String k2) {
        return redisTemplate.opsForHash().get(k1, k2);
    }

    public void putHash(String k1, HashMap<String, Object> info) {
        redisTemplate.opsForHash().putAll(k1, info);
    }

    public void putHashValue(String k1, String k2, Object v1) {
        redisTemplate.opsForHash().put(k1, k2, v1);
    }

    public void removeHash(String k1) {
        redisTemplate.delete(k1);
    }

    public HashMap<String, Object> getHash(String k1) {
        Map<Object, Object> map = redisTemplate.opsForHash().entries(k1);
        HashMap<String, Object> hashMap = new HashMap<>();
        map.forEach((k, v) -> {
            String newK = "";
            if (k instanceof String) {
                newK = (String) k;
            }
            hashMap.put(newK, v);
        });
        return hashMap;
    }

    public ChannelHandlerContext getCtx(String longId) {
        Object o = redisTemplate.opsForHash().get(longId, "ctx");
        if (o instanceof ChannelHandlerContext) {
            return (ChannelHandlerContext) o;
        } else {
            throw new RuntimeException("获取ctx出错");
        }
    }
}

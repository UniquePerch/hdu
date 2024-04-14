package com.hdu.hdufpga.listener;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Validator;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;
import com.hdu.hdufpga.netty.NettySocketHolder;
import com.hdu.hdufpga.service.CircuitBoardService;
import com.hdu.hdufpga.service.WaitingService;
import com.hdu.hdufpga.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {
    @Resource
    RedisUtil redisUtil;

    @Resource
    CircuitBoardService circuitBoardService;

    @Resource
    WaitingService waitingService;

    @Value("${wait-queue.name}")
    String queueName;

    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String expiredKey = message.toString();
        try {
            String[] split = expiredKey.split(":");
            String token = split[1];
            if (RedisConstant.REDIS_TTL_PREFIX.contains(split[0])) {
                freeBoardAndFreezeConnection(token);
            }
            if (RedisConstant.REDIS_BOARD_SERVER_PREFIX.contains(split[0])) {
                NettySocketHolder.remove(token);
                circuitBoardService.deleteByLongId(token);
                log.error("板卡:{}，失去连接，已经从缓存与数据库中移除", token);
            }
            if (RedisConstant.REDIS_CONN_SHADOW_PREFIX.contains(split[0])) {
                circuitBoardService.clearUserRedisAndFreeCB(token);
            }
        } catch (Exception e) {
            log.error(e.toString());
            log.error(expiredKey + " 有key过期了，但是没有成功执行监听方法");
        }
    }

    private void freeBoardAndFreezeConnection(String token) {
        if (Validator.isNotNull(redisUtil.getZSetRank(queueName, token))) {
            // 如果用户还在队伍中
            redisUtil.removeInZSet(queueName, token);
        }
        UserConnectionVO userConnectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
        String freeRes = circuitBoardService.freeCircuitBoard(userConnectionVO.getCbIp());
        if (Validator.isNull(freeRes)) {
            log.error("板卡资源:{} 释放失败", userConnectionVO.getLongId());
        } else {
            log.info("板卡资源:{} 释放成功", userConnectionVO.getLongId());
        }
        if (waitingService.freezeConnection(token)) {
            log.info("冻结用户连接:{} 成功", token);
        } else {
            log.error("冻结用户连接:{} 失败", token);
        }
    }
}

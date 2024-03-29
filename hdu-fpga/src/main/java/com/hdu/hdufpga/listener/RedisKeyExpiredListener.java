package com.hdu.hdufpga.listener;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Validator;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;
import com.hdu.hdufpga.service.CircuitBoardService;
import com.hdu.hdufpga.service.WaitingService;
import com.hdu.hdufpga.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
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
                UserConnectionVO userConnectionVO = Convert.convert(UserConnectionVO.class, redisUtil.get(RedisConstant.REDIS_CONN_PREFIX + token));
                String freeRes = circuitBoardService.freeCircuitBoard(userConnectionVO.getLongId());
                if (Validator.isNull(freeRes)) {
                    log.error("板卡资源:{} 释放失败", userConnectionVO.getLongId());
                }
                if (waitingService.freezeConnection(token)) {
                    log.info("冻结用户连接:{} 成功", token);
                } else {
                    log.error("冻结用户连接:{} 失败", token);
                }
            }
        } catch (Exception e) {
            log.error(expiredKey + " 有key过期了，但是没有成功执行监听方法");
        }
    }
}

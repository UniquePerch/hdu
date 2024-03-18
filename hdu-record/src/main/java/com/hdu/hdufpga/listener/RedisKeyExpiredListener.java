package com.hdu.hdufpga.listener;

import com.hdu.hdufpga.entity.po.VisitRecordPO;
import com.hdu.hdufpga.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import javax.annotation.Resource;

@Slf4j
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {
    @Resource
    RocketMQTemplate rocketMQTemplate;
    public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        rocketMQTemplate.asyncSend("visitRecord", new VisitRecordPO(message.toString(), TimeUtil.getNowTime()), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable throwable) {
                log.error("更新访问量失败，报错:{}",throwable.getMessage());
            }
        });
    }
}

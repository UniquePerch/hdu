package com.hdu.hdufpga.listener;

import com.hdu.hdufpga.entity.po.VisitRecordPO;
import com.hdu.hdufpga.mapper.VisitRecordMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@RocketMQMessageListener(consumerGroup = "visitRecord", topic = "visitRecord")
@Slf4j
public class MQVisitRecordListener implements RocketMQListener<VisitRecordPO> {
    @Resource
    VisitRecordMapper visitRecordMapper;

    @Override
    public void onMessage(VisitRecordPO visitRecordPO) {
        String sessionWithPre = visitRecordPO.getSessionId();
        String session = sessionWithPre.split(":")[1];
        visitRecordPO.setSessionId(session);
        int res = visitRecordMapper.insert(visitRecordPO);
        if (res <= 0) {
            log.error("数据库更新失败:{}", visitRecordMapper.getClass().getName());
            throw new RuntimeException("更新数据库表失败");
        }
    }
}

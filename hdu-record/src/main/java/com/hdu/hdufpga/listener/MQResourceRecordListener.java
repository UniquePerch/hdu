package com.hdu.hdufpga.listener;

import com.hdu.hdufpga.entity.po.UserResourceRecordPO;
import com.hdu.hdufpga.entity.vo.UserResourceRecordVO;
import com.hdu.hdufpga.mapper.StudentResourceRecordMapper;
import com.hdu.hdufpga.util.ConvertUtil;
import com.hdu.hdufpga.util.TimeUtil;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@RocketMQMessageListener(topic = "resourceRecord", consumerGroup = "resourceRecord")
@Component
public class MQResourceRecordListener implements RocketMQListener<UserResourceRecordVO> {
    @Resource
    private StudentResourceRecordMapper studentResourceRecordMapper;

    @Override
    public void onMessage(UserResourceRecordVO userResourceRecordVO) {
        UserResourceRecordPO userResourceRecordPO = ConvertUtil.copy(userResourceRecordVO, UserResourceRecordPO.class);
        userResourceRecordPO.setCreateTime(TimeUtil.getNowTime());
        userResourceRecordPO.setUpdateTime(TimeUtil.getNowTime());
        if (studentResourceRecordMapper.updateUserResourceRecord(userResourceRecordPO) == 0) {
            int res = studentResourceRecordMapper.insert(userResourceRecordPO);
            if (res == 0) {
                throw new RuntimeException("更新学生资源记录失败");
            }
        }
    }
}

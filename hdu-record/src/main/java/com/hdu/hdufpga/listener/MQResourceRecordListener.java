package com.hdu.hdufpga.listener;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
        Integer res;
        UserResourceRecordPO userResourceRecordPO = ConvertUtil.copy(userResourceRecordVO, UserResourceRecordPO.class);
        if(checkRecordExist(userResourceRecordPO)) {
            userResourceRecordPO.setUpdateTime(TimeUtil.getNowTime());
            res = studentResourceRecordMapper.updateUserResourceRecord(userResourceRecordPO);
        } else {
            userResourceRecordPO.setCreateTime(TimeUtil.getNowTime());
            userResourceRecordPO.setUpdateTime(TimeUtil.getNowTime());
            userResourceRecordPO.setTimes(1);
            res = studentResourceRecordMapper.insert(userResourceRecordPO);
        }
        if(res == 0) {
            throw new RuntimeException("更新学生资源观看记录失败");
        }
    }

    private Boolean checkRecordExist(UserResourceRecordPO userResourceRecordPO) {
        LambdaQueryWrapper<UserResourceRecordPO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(UserResourceRecordPO::getUserId, userResourceRecordPO.getUserId())
                .eq(UserResourceRecordPO::getResourceId, userResourceRecordPO.getResourceId());
        return studentResourceRecordMapper.selectCount(queryWrapper) > 0;
    }
}

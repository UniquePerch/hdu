package com.hdu.hdufpga.service.impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.po.TestRecordPO;
import com.hdu.hdufpga.mapper.TestRecordMapper;
import com.hdu.hdufpga.service.TestRecordService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@DubboService
public class TestRecordServiceImpl extends MPJBaseServiceImpl<TestRecordMapper, TestRecordPO> implements TestRecordService {
    @Resource
    TestRecordMapper testRecordMapper;
    @Override
    public Double getMaxScore(Integer userId, Integer classId) {
        return testRecordMapper.getMaxScore(userId, classId);
    }
}

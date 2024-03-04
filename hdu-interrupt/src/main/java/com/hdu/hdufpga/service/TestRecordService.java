package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.TestRecordPO;

public interface TestRecordService extends MPJBaseService<TestRecordPO> {
    Double getMaxScore(Integer userId, Integer classId);
}

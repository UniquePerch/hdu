package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.UserResourceRecordPO;
import com.hdu.hdufpga.entity.vo.StudentStudyRecord;
import com.hdu.hdufpga.entity.vo.UserResourceRecordVO;

import java.util.List;

public interface UserResourceRecordService extends MPJBaseService<UserResourceRecordPO> {
    Boolean updateResourceRecord(UserResourceRecordVO userResourceRecordVO);

    List<StudentStudyRecord> getRecordByClass(Integer classId);
}

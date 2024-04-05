package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.CbUseRecordPO;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;

public interface CbUseRecordService extends MPJBaseService<CbUseRecordPO> {
    Boolean saveUseRecord(UserConnectionVO userConnectionVO);
}

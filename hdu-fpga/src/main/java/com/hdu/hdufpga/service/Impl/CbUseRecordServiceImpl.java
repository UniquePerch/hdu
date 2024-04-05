package com.hdu.hdufpga.service.Impl;

import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.hdufpga.entity.constant.RedisConstant;
import com.hdu.hdufpga.entity.po.CbUseRecordPO;
import com.hdu.hdufpga.entity.vo.UserConnectionVO;
import com.hdu.hdufpga.mapper.CbUseRecordMapper;
import com.hdu.hdufpga.service.CbUseRecordService;
import com.hdu.hdufpga.util.TimeUtil;
import org.springframework.stereotype.Service;

@Service
public class CbUseRecordServiceImpl extends MPJBaseServiceImpl<CbUseRecordMapper, CbUseRecordPO> implements CbUseRecordService {

    @Override
    public Boolean saveUseRecord(UserConnectionVO userConnectionVO) {
        CbUseRecordPO cbUseRecordPO = new CbUseRecordPO();
        String token = userConnectionVO.getToken();
        String[] info = token.split("_");
        cbUseRecordPO.setCbId(userConnectionVO.getLongId());
        cbUseRecordPO.setCbIp(userConnectionVO.getCbIp());
        cbUseRecordPO.setUserName(info[0]);
        cbUseRecordPO.setUserIp(userConnectionVO.getUserIp());
        cbUseRecordPO.setDepartmentName(info[1]);
        cbUseRecordPO.setFileUploadTime(1);
        cbUseRecordPO.setDuration((int) (RedisConstant.REDIS_CONN_SHADOW_LIMIT - userConnectionVO.getLeftSecond()));
        cbUseRecordPO.setCreateTime(TimeUtil.getNowTime());
        cbUseRecordPO.setUpdateTime(TimeUtil.getNowTime());
        return save(cbUseRecordPO);
    }
}

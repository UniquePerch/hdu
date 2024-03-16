package com.hdu.hdufpga.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.hdu.hdufpga.entity.po.UserResourceRecordPO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentResourceRecordMapper extends MPJBaseMapper<UserResourceRecordPO> {
    Integer updateUserResourceRecord(UserResourceRecordPO userResourceRecordPO);
}

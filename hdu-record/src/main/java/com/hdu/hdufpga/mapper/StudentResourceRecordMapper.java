package com.hdu.hdufpga.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.hdu.hdufpga.entity.po.UserResourceRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface StudentResourceRecordMapper extends MPJBaseMapper<UserResourceRecordPO> {
    Integer updateUserResourceRecord(UserResourceRecordPO userResourceRecordPO);

    Long getStudnetResourceDurationSum(@Param("date1")Date d1,@Param("date2") Date d2);
}

package com.hdu.hdufpga.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.hdu.hdufpga.entity.po.TestRecordPO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestRecordMapper extends MPJBaseMapper<TestRecordPO> {
    Double getMaxScore(@Param("userId") Integer userId, @Param("classId") Integer classId);

}

package com.hdu.hdufpga.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import com.hdu.hdufpga.entity.po.Problem1PO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProblemMapper extends MPJBaseMapper<Problem1PO> {
    List<Problem1PO> getRandomProblems(Integer limit);
}

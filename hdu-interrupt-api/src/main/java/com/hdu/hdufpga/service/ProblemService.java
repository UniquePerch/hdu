package com.hdu.hdufpga.service;

import com.github.yulichang.base.MPJBaseService;
import com.hdu.hdufpga.entity.po.Problem1PO;
import com.hdu.hdufpga.entity.vo.Problem1VO;

import java.util.List;
import java.util.Map;

public interface ProblemService extends MPJBaseService<Problem1PO> {
    List<Problem1VO> getProblems();

    Map<String, Double> checkAnswer(Integer userId, Integer classId, List<Problem1VO> voList);
}

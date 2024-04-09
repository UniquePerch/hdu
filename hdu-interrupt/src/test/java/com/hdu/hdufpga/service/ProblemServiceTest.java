package com.hdu.hdufpga.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.hdu.hdufpga.entity.BaseEntity;
import com.hdu.hdufpga.entity.po.Problem1PO;
import com.hdu.hdufpga.entity.vo.Problem1VO;
import com.hdu.hdufpga.mapper.ProblemMapper;
import com.hdu.hdufpga.util.ConvertUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class ProblemServiceTest {
    @Resource
    private ProblemService problemService;

    @Resource
    private ProblemMapper problemMapper;

    @Test
    void testProblemServiceGetProblems() {
        List<Problem1VO> problem1VOS = problemService.getProblems();
        problem1VOS.forEach(System.out::println);
    }

    @Test
    void testProblemServiceCheckAnswer() {
        LambdaQueryWrapper<Problem1PO> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(BaseEntity::getId, 1, 2, 3, 4);
        List<Problem1PO> poList = problemMapper.selectList(wrapper);
        List<Problem1VO> voList = ConvertUtil.copyList(poList, Problem1VO.class);
        voList.get(0).setAnswer("B");
        voList.get(1).setAnswer("A");
        System.out.println(problemService.checkAnswer(511, 3, voList));
    }
}

package com.hdu.hdufpga.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.hdu.entity.BaseEntity;
import com.hdu.entity.constant.ProblemConstant;
import com.hdu.hdufpga.entity.po.Problem1PO;
import com.hdu.hdufpga.entity.po.TestRecordPO;
import com.hdu.hdufpga.entity.vo.Problem1VO;
import com.hdu.hdufpga.mapper.ProblemMapper;
import com.hdu.hdufpga.mapper.TestRecordMapper;
import com.hdu.hdufpga.service.ProblemService;
import com.hdu.util.ConvertUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProblemServiceImpl extends MPJBaseServiceImpl<ProblemMapper, Problem1PO> implements ProblemService {
    @Resource
    ProblemMapper problemMapper;

    @Resource
    TestRecordMapper testRecordMapper;
    @Override
    public List<Problem1VO> getProblems() {
        List<Problem1PO> poList = problemMapper.getRandomProblems(ProblemConstant.PROBLEM_LIMIT);
        return ConvertUtil.copyList(poList, Problem1VO.class);
    }

    @Override
    @Transactional
    public Map<String, Double> checkAnswer(Integer userId, Integer classId, List<Problem1VO> voList) {
        TestRecordPO testRecordPO = new TestRecordPO();
        testRecordPO.setUserId(userId);
        testRecordPO.setClassId(classId);

        List<Integer> problemIds = new ArrayList<>();
        voList.forEach(vo -> problemIds.add(vo.getId()));
        LambdaQueryWrapper<Problem1PO> wrapper = new LambdaQueryWrapper<>();
        wrapper
                .select(BaseEntity::getId,Problem1PO::getAnswer)
                .in(BaseEntity::getId,problemIds);
        List<Problem1PO> poList = problemMapper.selectList(wrapper);
        // 排序
        poList.sort(Problem1PO::compareTo);
        voList.sort(Problem1VO::compareTo);

        testRecordPO.setProblemsJson(JSONUtil.toJsonStr(poList));
        testRecordPO.setChoicesJson(JSONUtil.toJsonStr(voList));
        if(voList.size() != poList.size()) {
            throw new RuntimeException("查询答案出错，选择和答案数量不符");
        }
        double score = 0.0;
        for(int i = 0; i < voList.size(); i++) {
            if(!voList.get(i).getId().equals(poList.get(i).getId())) {
                throw new RuntimeException("比较答案时题目顺序不符合标准");
            }
            String choice = voList.get(i).getAnswer();
            String answer = poList.get(i).getAnswer();
            if(choice.equals(answer)) score += 100.0 / voList.size();
        }
        testRecordPO.setScore(score);
        testRecordMapper.insert(testRecordPO);
        Double maxScore = testRecordMapper.getMaxScore(userId,classId);
        Map<String,Double> res = new HashMap<>();
        res.put("maxScore",maxScore);
        res.put("nowScore",score);
        return res;
    }
}

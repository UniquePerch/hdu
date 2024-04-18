package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.Problem1PO;
import com.hdu.hdufpga.entity.vo.Problem1VO;
import com.hdu.hdufpga.service.ProblemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/problem")
@Slf4j
public class ProblemController extends BaseController<ProblemService, Problem1PO> {
    //level >= 3
    @Override
    public Result create(Problem1PO problem1PO) {
        return super.create(problem1PO);
    }

    //level >= 3
    @Override
    public Result delete(Problem1PO problem1PO) {
        return super.delete(problem1PO);
    }

    //level >= 3
    @Override
    public Result update(Problem1PO problem1PO) {
        return super.update(problem1PO);
    }

    //level >= 3
    @Override
    public Result listPage(Integer current, Integer size) {
        return super.listPage(current, size);
    }

    //level >= 3
    @Override
    public Result get(Integer id) {
        return super.get(id);
    }

    //level >= 1
    @RequestMapping("/getProblems")
    public Result getProblems() {
        try {
            return Result.ok(service.getProblems());
        } catch (Exception e) {
            log.error("获取题目列表失败", e);
            return Result.error("获取题目列表失败");
        }
    }

    //level >= 1
    @RequestMapping("/checkAnswer")
    public Result checkAnswer(Integer userId, Integer classId, List<Problem1VO> voList) {
        try {
            return Result.ok(service.checkAnswer(userId, classId, voList));
        } catch (Exception e) {
            log.error("打分失败", e);
            return Result.error("打分失败");
        }
    }
}

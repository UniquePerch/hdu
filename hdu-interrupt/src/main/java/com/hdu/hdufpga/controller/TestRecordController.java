package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.TestRecordPO;
import com.hdu.hdufpga.service.TestRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testRecord")
@Slf4j
public class TestRecordController extends BaseController<TestRecordService, TestRecordPO> {
    @Override
    public Result create(TestRecordPO testRecordPO) {
        return Result.error("此接口不支持本方法");
    }

    //level >= 2
    @Override
    public Result delete(TestRecordPO testRecordPO) {
        return super.delete(testRecordPO);
    }

    //level >= 2
    @Override
    public Result update(TestRecordPO testRecordPO) {
        return super.update(testRecordPO);
    }

    //level >= 2
    @Override
    public Result listPage(Integer current, Integer size) {
        return super.listPage(current, size);
    }

    //level >= 2
    @Override
    public Result get(Integer id) {
        return super.get(id);
    }

    //level >= 1
    @GetMapping("/getMaxScore")
    public Result getMaxScore(Integer userId, Integer classId) {
        try {
            return Result.ok(service.getMaxScore(userId, classId));
        } catch (Exception e) {
            log.error("获取最高分失败", e);
            return Result.error("获取最高分失败");
        }
    }
}

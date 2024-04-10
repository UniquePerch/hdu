package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.vo.UserResourceRecordVO;
import com.hdu.hdufpga.service.UserResourceRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/studentStudyRecord")
@Slf4j
public class StudentStudyRecordController {
    @Resource
    UserResourceRecordService userResourceRecordService;

    @PostMapping("/updateResourceRecord")
    public Result updateResourceRecord(@RequestBody UserResourceRecordVO userResourceRecordVO) {
        try {
            return Result.ok(userResourceRecordService.updateResourceRecord(userResourceRecordVO));
        } catch (Exception e) {
            log.error("updateResourceRecord error", e);
            return Result.error();
        }
    }

    @GetMapping("/getRecordByClass")
    public Result getRecordByClass(Integer classId) {
        try {
            return Result.ok(userResourceRecordService.getRecordByClass(classId));
        } catch (Exception e) {
            log.error("getRecordByClass error", e);
            return Result.error();
        }
    }
}

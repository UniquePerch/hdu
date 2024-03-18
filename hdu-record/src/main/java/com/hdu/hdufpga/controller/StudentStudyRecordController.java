package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.vo.UserResourceRecordVO;
import com.hdu.hdufpga.service.UserResourceRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/co/studentStudyRecord")
@Slf4j
public class StudentStudyRecordController {
    @Resource
    UserResourceRecordService userResourceRecordService;

    @RequestMapping("/updateResourceRecord")
    public Result updateResourceRecord(UserResourceRecordVO userResourceRecordVO){
        try {
            return Result.ok(userResourceRecordService.updateResourceRecord(userResourceRecordVO));
        } catch (Exception e) {
            log.error("updateResourceRecord error",e);
            return Result.error();
        }
    }

    @RequestMapping("/getRecordByClass")
    public Result getRecordByClass(Integer classId){
        try {
            return Result.ok(userResourceRecordService.getRecordByClass(classId));
        } catch (Exception e) {
            log.error("getRecordByClass error",e);
            return Result.error();
        }
    }
}

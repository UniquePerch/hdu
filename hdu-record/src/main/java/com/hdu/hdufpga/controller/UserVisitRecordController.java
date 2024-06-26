package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.service.UserRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

@RestController
@RequestMapping("/userVisitRecord")
@Slf4j
public class UserVisitRecordController {
    @Resource
    UserRecordService userRecordService;

    //level >= 3
    @GetMapping("/getUserVisitInfo")
    public Result getUserVisitInfo(@DateTimeFormat(pattern = "yyyy-MM-dd") Date startTime, @DateTimeFormat(pattern = "yyyy-MM-dd") Date endTime) {
        try {
            return Result.ok(userRecordService.getUserVisitInfo(startTime, endTime));
        } catch (Exception e) {
            log.error("获取用户访问数据失败 : {}", e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}

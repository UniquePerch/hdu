package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.ChapterPO;
import com.hdu.hdufpga.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/co/capter")
@Slf4j
public class ChapterController extends BaseController<ChapterService, ChapterPO> {
    @RequestMapping("/recordFinish")
    public Result recordFinish(Integer userId, Integer chapterId) {
        try {
            if (service.recordFinish(userId, chapterId)) {
                return Result.ok("记录学习记录成功");
            } else {
                return Result.error("已经学习过该知识点");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("数据库原因，记录学习记录失败");
        }
    }

    @RequestMapping("/getAllChapterRecord")
    public Result getAllChapterRecord() {
        try {
            return Result.ok(service.getAllChapterRecord());
        } catch (Exception e) {
            log.error(e.getMessage());
            if (e.getMessage().toLowerCase().contains("unique")) {
                return Result.ok("已经学习过该知识点");
            }
            return Result.error("数据库原因，获取学习记录失败");
        }
    }

    @RequestMapping("/getChapterRecordByUserId")
    public Result getChapterRecordByUserId(Integer userId) {
        try {
            return Result.ok(service.getChapterRecordByUserId(userId));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("数据库原因，获取学习记录失败");
        }
    }
}

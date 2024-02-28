package com.hdu.hdufpga.controller;

import com.hdu.controller.BaseController;
import com.hdu.entity.Result;
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
    public Result recordFinish(Integer userId,Integer chapterId){
        try {
            if(service.recordFinish(userId,chapterId)){
                return Result.ok("记录学习记录成功");
            } else {
                return Result.error("记录学习记录失败");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("数据库原因，记录学习记录失败");
        }
    }

    @RequestMapping("/getAllChapterRecord")
    public Result getAllChapterRecord(){
        try {
            return Result.ok(service.getAllChapterRecord());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("数据库原因，获取学习记录失败");
        }
    }
}

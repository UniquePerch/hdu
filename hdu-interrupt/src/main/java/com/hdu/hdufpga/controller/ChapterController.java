package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.ChapterPO;
import com.hdu.hdufpga.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chapter")
@Slf4j
public class ChapterController extends BaseController<ChapterService, ChapterPO> {
    //level >= 3
    @Override
    public Result create(@RequestBody ChapterPO chapterPO) {
        return super.create(chapterPO);
    }

    //level >= 3
    @Override
    public Result update(@RequestBody ChapterPO chapterPO) {
        return super.update(chapterPO);
    }

    //level >= 3
    @Override
    public Result delete(@RequestBody ChapterPO chapterPO) {
        return super.delete(chapterPO);
    }

    //level >= 3
    @Override
    public Result get(Integer id) {
        return super.get(id);
    }

    //level >= 3
    @Override
    public Result listPage(Integer current, Integer size) {
        return super.listPage(current, size);
    }

    //level >= 1
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

    //level >= 3
    @RequestMapping("/getAllChapterRecord")
    public Result getAllChapterRecord(Integer current, Integer size) {
        try {
            return Result.ok(service.getAllChapterRecord(current, size));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error("数据库原因，获取学习记录失败");
        }
    }

    //level >= 1
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

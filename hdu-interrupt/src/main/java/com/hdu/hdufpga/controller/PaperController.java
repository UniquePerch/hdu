package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.vo.HandInInfoVO;
import com.hdu.hdufpga.entity.vo.PaperVO;
import com.hdu.hdufpga.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/paper")
@Slf4j
public class PaperController {
    @Resource
    private PaperService paperService;

    @PostMapping(value = "/uploadPaper", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result uploadPaper(@RequestBody PaperVO paperVO) {
        try {
            return Result.ok(paperService.uploadPaper(paperVO));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @PostMapping(value = "/deletePaper")
    public Result deletePaper(@RequestBody PaperVO paperVO) {
        try {
            return Result.ok(paperService.deletePaper(paperVO));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @GetMapping("/getAllPaperByClassId")
    public Result getPapersByClassId(Integer classId) {
        try {
            return Result.ok(paperService.getPapersByClassId(classId));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @PostMapping(value = "/handInPaper", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result handInPaper(HandInInfoVO handInInfoVO) {
        try {
            return Result.ok(paperService.handInPaper(handInInfoVO));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/getHandInInfoByClassId")
    public Result getHandInInfoByClassId(Integer classId) {
        try {
            return Result.ok(paperService.getHandInInfoByClassId(classId));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @GetMapping("/getHandInInfoByUserId")
    public Result getHandInInfoByUserId(Integer userId) {
        try {
            return Result.ok(paperService.getHandInInfoByUserId(userId));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @PostMapping(value = "/updateHandInInfo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result updateHandInInfo(HandInInfoVO handInInfoVO) {
        try {
            return Result.ok(paperService.updateHandInInfo(handInInfoVO));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/correctingPaper")
    public Result correctingPaper(@RequestBody HandInInfoVO handInInfoVO) {
        try {
            return Result.ok(paperService.correctingPaper(handInInfoVO));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    //打回报告
    @PostMapping("/returnPaper")
    public Result returnPaper(@RequestBody HandInInfoVO handInInfoVO) {
        try {
            return Result.ok(paperService.returnPaper(handInInfoVO.getId()));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }
}

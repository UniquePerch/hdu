package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.PaperPO;
import com.hdu.hdufpga.entity.vo.HandInInfoVO;
import com.hdu.hdufpga.entity.vo.PaperVO;
import com.hdu.hdufpga.service.PaperService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/co/paper")
@Slf4j
public class PaperController extends BaseController<PaperService, PaperPO> {
    @RequestMapping(value = "/uploadPaper", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result uploadPaper(@RequestBody PaperVO paperVO) {
        try {
            return Result.ok(service.uploadPaper(paperVO));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @RequestMapping("/getAllPaperByClassId")
    public Result getPapersByClassId(Integer classId) {
        try {
            return Result.ok(service.getPapersByClassId(classId));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @RequestMapping(value = "/handInPaper", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Result handInPaper(HandInInfoVO handInInfoVO) {
        try {
            return Result.ok(service.handInPaper(handInInfoVO));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @RequestMapping("/getHandInInfoByClassId")
    public Result getHandInInfoByClassId(Integer classId) {
        try {
            return Result.ok(service.getHandInInfoByClassId(classId));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    @RequestMapping("/updateHandInInfo")
    public Result updateHandInInfo(HandInInfoVO handInInfoVO) {
        try {
            return Result.ok(service.updateHandInInfo(handInInfoVO));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    @RequestMapping("/correctingPaper")
    public Result correctingPaper(HandInInfoVO handInInfoVO) {
        try {
            return Result.ok(service.correctingPaper(handInInfoVO));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }

    //打回报告
    @RequestMapping("/returnPaper")
    public Result returnPaper(HandInInfoVO handInInfoVO) {
        try {
            return Result.ok(service.returnPaper(handInInfoVO.getId()));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error();
        }
    }
}

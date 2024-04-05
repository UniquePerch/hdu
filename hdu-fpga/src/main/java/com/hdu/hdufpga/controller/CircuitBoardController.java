package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.annotation.CheckAndRefreshToken;
import com.hdu.hdufpga.annotation.CheckToken;
import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.service.CircuitBoardHistoryOperationService;
import com.hdu.hdufpga.service.CircuitBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/co/fpga")
@Slf4j
public class CircuitBoardController extends BaseController<CircuitBoardService, CircuitBoardPO> {
    @Resource
    CircuitBoardService circuitBoardService;

    @Resource
    CircuitBoardHistoryOperationService circuitBoardHistoryOperationService;

    @PostMapping("/finish")
    @CheckToken
    public Result finish(HttpServletRequest request) {
        String token = request.getHeader("token");
        try {
            return Result.ok(circuitBoardService.clearUserRedisAndFreeCB(token));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/getRecordedStatus")
    @CheckAndRefreshToken
    public Result getRecordedStatus(HttpServletRequest request, String cbIp) {
        String token = request.getHeader("token");
        try {
            return Result.ok(circuitBoardService.getRecordStatus(token, cbIp));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/sendButtonString")
    @CheckAndRefreshToken
    public Result sendButtonString(HttpServletRequest request, String switchButtonStatus, String tapButtonStatus) {
        String token = request.getHeader("token");
        try {
            return Result.ok(circuitBoardService.sendButtonString(token, switchButtonStatus, tapButtonStatus));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/getLightString")
    @CheckAndRefreshToken
    public Result getLightString(HttpServletRequest request) {
        String token = request.getHeader("token");
        try {
            return Result.ok(circuitBoardService.getLightString(token));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/getNixieTubeString")
    @CheckAndRefreshToken
    public Result getNixieTubeString(HttpServletRequest request) {
        String token = request.getHeader("token");
        try {
            return Result.ok(circuitBoardService.getNixieTubeString(token));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/getProcessedBtnStr")
    @CheckAndRefreshToken
    public Result getProcessedBtnStr(HttpServletRequest request) {
        String token = request.getHeader("token");
        try {
            return Result.ok(circuitBoardService.getProcessedBtnStr(token));
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/loadHistory")
    @CheckAndRefreshToken
    public Result loadHistory(HttpServletRequest request, Boolean tag) {
        String token = request.getHeader("token");
        try {
            if (tag) {
                if (circuitBoardHistoryOperationService.loadOperationHistory(token)) {
                    return Result.ok("载入成功");
                } else {
                    return Result.error("载入失败");
                }
            } else {
                circuitBoardHistoryOperationService.clearSteps(token);
                return Result.ok("初始化成功");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}

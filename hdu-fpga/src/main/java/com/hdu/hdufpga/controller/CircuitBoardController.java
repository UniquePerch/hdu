package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.annotation.CheckAndRefreshToken;
import com.hdu.hdufpga.annotation.CheckToken;
import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.po.CircuitBoardPO;
import com.hdu.hdufpga.service.CircuitBoardHistoryOperationService;
import com.hdu.hdufpga.service.CircuitBoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/cb")
@Slf4j
public class CircuitBoardController extends BaseController<CircuitBoardService, CircuitBoardPO> {
    @Resource
    CircuitBoardService circuitBoardService;

    @Resource
    CircuitBoardHistoryOperationService circuitBoardHistoryOperationService;

    //level >= 3
    @Override
    public Result listPage(Integer current, Integer size) {
        return super.listPage(current, size);
    }

    //level >= 3
    @Override
    public Result get(Integer id) {
        return super.get(id);
    }

    @Override
    public Result create(@RequestBody CircuitBoardPO circuitBoardPO) {
        return Result.error("不支持本方法");
    }

    @Override
    public Result update(@RequestBody CircuitBoardPO circuitBoardPO) {
        return Result.error("不支持本方法");
    }

    @Override
    public Result delete(@RequestBody CircuitBoardPO circuitBoardPO) {
        return Result.error("不支持本方法");
    }

    //level >= 1
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

    //level >= 1
    @GetMapping("/getRecordedStatus")
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

    //level >= 1
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

    //level >= 1
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

    //level >= 1
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

    //level >= 1
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

    //level >= 1
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

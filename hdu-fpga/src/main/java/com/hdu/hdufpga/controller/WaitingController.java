package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.annotation.CheckAndRefreshToken;
import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.service.WaitingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/co/waiting")
public class WaitingController {
    @Resource
    WaitingService waitingService;

    @PostMapping("/inLine")
    @CheckAndRefreshToken
    public Result userInLine(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            return Result.ok(waitingService.userInQueue(token));
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/checkAvailability")
    @CheckAndRefreshToken
    public Result checkAvailability(HttpServletRequest request) {
        try {
            String token = request.getHeader("token");
            return waitingService.checkAvailability(token);
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }
}

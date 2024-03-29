package com.hdu.hdufpga.controller;

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
    public Result userInLine(HttpServletRequest request) {
        String token = request.getHeader("token");
        return null;
    }
}

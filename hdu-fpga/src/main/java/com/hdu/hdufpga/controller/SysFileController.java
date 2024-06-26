package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.annotation.CheckAndRefreshToken;
import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.service.SysFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
@RequestMapping("/file")
public class SysFileController {
    @Resource
    SysFileService sysFileService;

    //level >= 1
    @PostMapping(value = "/uploadBit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @CheckAndRefreshToken
    public Result uploadBit(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
        try {
            return Result.ok(sysFileService.uploadBit(request, file));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }

    //level >= 1
    @PostMapping("/reloadBitFile")
    @CheckAndRefreshToken
    public Result reloadBitFile(HttpServletRequest request) {
        String token = request.getHeader("token");
        try {
            return Result.ok(sysFileService.reloadBitFile(token));
        } catch (Exception e) {
            log.error(e.toString());
            return Result.error(e.getMessage());
        }
    }
}

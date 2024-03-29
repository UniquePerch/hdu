package com.hdu.hdufpga.controller;

import cn.hutool.json.JSONUtil;
import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.entity.vo.UserVO;
import com.hdu.hdufpga.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/co/fpga")
@Slf4j
public class TokenController {
    @Resource
    TokenService tokenService;

    @PostMapping("/generateToken")
    public Result generateToken(HttpServletRequest request) {
        try {
            UserVO userVO = JSONUtil.toBean(request.getHeader("login_user"), UserVO.class);
            return tokenService.generateToken(userVO);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Result.error(e.getMessage());
        }
    }

}

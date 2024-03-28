package com.hdu.hdufpga.controller;

import com.hdu.hdufpga.entity.Result;
import com.hdu.hdufpga.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sso")
public class AuthController {

    @Resource
    private AuthService authService;

    @PostMapping("/login")
    public Result<Object> login(String username, String password, String part) {
        return Result.ok(authService.login(username, password, part));
    }

    @GetMapping("/logout")
    public Result<Void> logout(String username) {
        authService.logout(username);
        return Result.ok();
    }

}

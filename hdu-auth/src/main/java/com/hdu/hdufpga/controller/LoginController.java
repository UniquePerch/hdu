package com.hdu.hdufpga.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    //TODO:完善登录接口
    @RequestMapping("/success")
    public String loginSuccess() {
        return "登录成功";
    }

    @RequestMapping("/fail")
    @ResponseBody
    public String loginFail() {
        return "登录失败";
    }
}

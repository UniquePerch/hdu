package com.hdu.hdufpga.service.Impl;

import cn.dev33.satoken.stp.StpUtil;
import com.hdu.hdufpga.service.AbstractSsoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountSsoService extends AbstractSsoService {

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public Object login(String username) {
        if (!StpUtil.isLogin(username)) {
            StpUtil.login(username);
        }
        return StpUtil.getSessionByLoginId(username);
    }

    @Override
    public void logout(String username) {
        StpUtil.logout(username);
    }

    @Override
    public String type() {
        return applicationName;
    }
}

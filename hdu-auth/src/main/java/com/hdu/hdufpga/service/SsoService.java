package com.hdu.hdufpga.service;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@Lazy
@Slf4j
public class SsoService {

    private final Set<AbstractSsoService> ssoServices = new HashSet<>();

    @DubboReference(group = "account")
    private AbstractSsoService accountSsoService;

    private void init() {
        if (ssoServices.isEmpty()) {
            ssoServices.add(accountSsoService);
        }
    }

    public AbstractSsoService getSsoService(String applicationName) {
        init();
        for (AbstractSsoService ssoService : ssoServices) {
            if (Objects.nonNull(ssoService) && StrUtil.equals(ssoService.getApplicationName(), applicationName)) {
                return ssoService;
            }
        }
        return null;
    }

    public Set<AbstractSsoService> getAllServices() {
        init();
        return ssoServices;
    }


}

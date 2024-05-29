package com.hdu.hdufpga.service.impl;

import com.hdu.hdufpga.service.AbstractSsoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@DubboService(group = "interruptSso")
@Slf4j
public class InterruptSsoService implements AbstractSsoService {

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public String getApplicationName() {
        log.info("getApplicationName:{}", applicationName);
        return applicationName;
    }
}

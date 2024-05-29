package com.hdu.hdufpga.service.Impl;

import com.hdu.hdufpga.service.AbstractSsoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@DubboService(group = "fpgaSso")
public class FpgaSsoService implements AbstractSsoService {
    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public String getApplicationName() {
        log.info("application name {}", applicationName);
        return applicationName;
    }
}

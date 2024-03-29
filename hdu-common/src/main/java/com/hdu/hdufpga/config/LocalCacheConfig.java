package com.hdu.hdufpga.config;

import com.hdu.hdufpga.util.LocalCache;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LocalCacheConfig {
    @Bean
    public LocalCache<String, Object> localCache() {
        return new LocalCache<String, Object>();
    }
}

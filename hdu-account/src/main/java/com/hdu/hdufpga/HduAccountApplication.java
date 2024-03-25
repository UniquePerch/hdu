package com.hdu.hdufpga;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableAutoDataSourceProxy
@EnableDubbo
@EnableResourceServer
public class HduAccountApplication {

    public static void main(String[] args) {
        SpringApplication.run(HduAccountApplication.class, args);
    }

}

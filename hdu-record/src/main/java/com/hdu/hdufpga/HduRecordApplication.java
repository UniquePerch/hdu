package com.hdu.hdufpga;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableDubbo
public class HduRecordApplication {

    public static void main(String[] args) {
        SpringApplication.run(HduRecordApplication.class, args);
    }

}
